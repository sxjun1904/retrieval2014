package framework.retrieval.engine.query.group;

import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.search.CachingCollector;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.SearchGroup;
import org.apache.lucene.search.grouping.TermAllGroupsCollector;
import org.apache.lucene.search.grouping.TermFirstPassGroupingCollector;
import org.apache.lucene.search.grouping.TermSecondPassGroupingCollector;
import org.apache.lucene.search.grouping.TopGroups;

import framework.retrieval.helper.RetrievalPages;

/**
 * 全文检索分组处理类
 * 
 * @author sxjun
 * @version [版本号, 2013-12-19]
 */
public class Grouping
{
    public void groupBy(IndexSearcher searcher, Query query, Sort groupSort, String groupField,RetrievalPages retrievalPages) throws IOException {
        int topNGroups = 30; // 每页需要多少个组
        int groupOffset = 0; // 起始的组
        boolean fillFields = true;
        Sort docSort = groupSort;
        // groupSort用于对组进行排序，docSort用于对组内记录进行排序，多数情况下两者是相同的，但也可不同
        // Sort docSort = new Sort(new SortField[] { new SortField("page",SortField.INT, true) });
        int docOffset = 0; // 用于组内分页，起始的记录
        int docsPerGroup = 1;// 每组返回多少条结果
        boolean requiredTotalGroupCount = true; // 是否需要计算总的组的数量

        // 如果需要对Lucene的score进行修正，则需要重载TermFirstPassGroupingCollector
        TermFirstPassGroupingCollector c1 = new TermFirstPassGroupingCollector(groupField, groupSort, groupOffset+topNGroups);

        boolean cacheScores = true;
        double maxCacheRAMMB = 16.0;
        CachingCollector cachedCollector = CachingCollector.create(c1, cacheScores, maxCacheRAMMB);
        searcher.search(query, cachedCollector);

        Collection<SearchGroup<String>> topGroups = c1.getTopGroups(groupOffset, fillFields);

        if (topGroups == null) {
            // No groups matched
            return;
        }

        Collector secondPassCollector = null;

        boolean getScores = true;
        boolean getMaxScores = true;
        // 如果需要对Lucene的score进行修正，则需要重载TermSecondPassGroupingCollector
        TermSecondPassGroupingCollector c2 = new TermSecondPassGroupingCollector(groupField, topGroups, groupSort,docSort, docOffset + docsPerGroup, getScores, getMaxScores, fillFields);

        // Optionally compute total group count
        TermAllGroupsCollector allGroupsCollector = null;
        if (requiredTotalGroupCount) {
            allGroupsCollector = new TermAllGroupsCollector(groupField);
            secondPassCollector = MultiCollector.wrap(c2, allGroupsCollector);
        }
        else {
            secondPassCollector = c2;
        }

        if (cachedCollector.isCached()) {
            // Cache fit within maxCacheRAMMB, so we can replay it:
            cachedCollector.replay(secondPassCollector);
        }
        else {
            // Cache was too large; must re-execute query:
            searcher.search(query, secondPassCollector);
        }

        int totalGroupCount = -1; // 所有组的数量
        int totalHitCount = -1; // 所有满足条件的记录数
        int totalGroupedHitCount = -1; // 所有组内的满足条件的记录数(通常该值与totalHitCount是一致的)
        if (requiredTotalGroupCount) {
            totalGroupCount = allGroupsCollector.getGroupCount();
        }
        System.out.println("groupCount: " + totalGroupCount);

        TopGroups<String> groupsResult = c2.getTopGroups(docOffset);
        totalHitCount = groupsResult.totalHitCount;
        totalGroupedHitCount = groupsResult.totalGroupedHitCount;
        System.out.println("groupsResult.totalHitCount:" + totalHitCount);
        System.out.println("groupsResult.totalGroupedHitCount:" + totalGroupedHitCount);

        // 迭代组
        for (GroupDocs<String> groupDocs : groupsResult.groups) {
        	retrievalPages.getGroup().put(groupDocs.groupValue, groupDocs.totalHits);
        }
    }

}
