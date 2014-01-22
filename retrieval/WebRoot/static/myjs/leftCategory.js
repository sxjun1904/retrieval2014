function getTemplate(o){
	var topArray = o.data;
	var loopHtml = "";
	for(var t = 0;t<topArray.length;t++){
		loopHtml += '<h2><a href="'+topArray[t].title.link+'">'+topArray[t].title.name+'</a></h2> ';
		var array = topArray[t].data;
		for(var i = 0 ;i<array.length;i++){
			loopHtml +='<div class="h2_cat" onmouseover="this.className=\'h2_cat active_cat\'" onmouseout="this.className=\'h2_cat\'"> '+
			'<h3><a href="'+array[i].title.link+'">'+array[i].title.name+'</a></h3> '+
			'<div class="h3_cat"> '+
				'<div class="shadow"> '+
					'<div class="shadow_border"> '+
						'<ul> ';
						var dataArray = array[i].data;
						for(var j=0;j<dataArray.length;j++){
							loopHtml +='<li><a href="'+dataArray[j].title.link+'">'+dataArray[j].title.name+'</a></li> ';
						}
			loopHtml+='</ul> '+
					'</div> '+
				'</div> '+
			'</div> '+
		'</div> ';
		}
	}
	var template = '<div class="my_left_category"> '+
						'<h1>'+o.title.name+'</h1> '+
						'<div class="my_left_cat_list"> '
							+loopHtml+
						'</div> '+
					'</div>';
	return template;
}

function demo1(){
	var o = {title:{name:'分类导航',link:'#'},
			data:[
					{title:{name:'按网站类别',link:'#'},
						data:[{title:{name:'企业建站',link:'#'},
							   data:[
							      {title:{name:'LOGO设计',link:'#'}},
							      {title:{name:'网站设计',link:'#'}},
							      {title:{name:'网站广告',link:'#'}},
							      {title:{name:'推广',link:'#'}},
							      {title:{name:'建网站',link:'#'}},
							      {title:{name:'网站推广',link:'#'}},
							      {title:{name:'网站建设',link:'#'}},
							      {title:{name:'SEO',link:'#'}}
							 ]},
							 {title:{name:'企业建站',link:'#'},
								   data:[
								      {title:{name:'LOGO设计',link:'#'}},
								      {title:{name:'网站设计',link:'#'}},
								      {title:{name:'网站广告',link:'#'}},
								      {title:{name:'推广',link:'#'}},
								      {title:{name:'建网站',link:'#'}},
								      {title:{name:'网站推广',link:'#'}},
								      {title:{name:'网站建设',link:'#'}},
								      {title:{name:'SEO',link:'#'}}
							 ]}
					]},
					{title:{name:'按网站类别',link:'#'},
						data:[{title:{name:'企业建站',link:'#'},
							   data:[
							      {title:{name:'LOGO设计',link:'#'}},
							      {title:{name:'网站设计',link:'#'}},
							      {title:{name:'网站广告',link:'#'}},
							      {title:{name:'推广',link:'#'}},
							      {title:{name:'建网站',link:'#'}},
							      {title:{name:'网站推广',link:'#'}},
							      {title:{name:'网站建设',link:'#'}},
							      {title:{name:'SEO',link:'#'}}
							 ]},
							 {title:{name:'企业建站',link:'#'},
								   data:[
								      {title:{name:'LOGO设计',link:'#'}},
								      {title:{name:'网站设计',link:'#'}},
								      {title:{name:'网站广告',link:'#'}},
								      {title:{name:'推广',link:'#'}},
								      {title:{name:'建网站',link:'#'}},
								      {title:{name:'网站推广',link:'#'}},
								      {title:{name:'网站建设',link:'#'}},
								      {title:{name:'SEO',link:'#'}}
							 ]}
					]}
			    ]
			};
	return o;
}

function divAttr(_div){
	this.data=_div.attr("data");
	this.demo=_div.attr("demo");
	return this;
}

function leftCatefory(div){
	var _div = $('#'+div);
	var obj = divAttr(_div);
	var o = obj.data;
	if(obj.demo=="true")
		o = demo1();
	else if(o=null){
		alert('您还没有选项值！');
		return;
	}
	_div.html(getTemplate(o));
}