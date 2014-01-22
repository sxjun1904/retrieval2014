 
function selectDown(_seft,targetValId,obj) {
	var targetId = $("#selectItem");

	_seft.click(function(){
		var A_top = $(this).offset().top + $(this).outerHeight(true);  //  1
		var A_left =  $(this).offset().left;
		//targetId.bgiframe();
		targetId.find("#selectSub :checkbox").attr("checked",false);
		var targetVal = targetValId.val().split(";");
		for(var i=0;i<targetVal.length-1;i++){
			var checkid = "#cr"+targetVal[i];
			targetId.find(checkid).attr("checked",true);
		}
		targetId.show().css({"position":"absolute","top":A_top+"px" ,"left":A_left+"px"});
	});

	targetId.find("#selectItemClose").click(function(){
		targetId.hide();
	});

	targetId.find("#selectItemSubmit").click(function(){
		var checkArr = targetId.find(":checkbox");
		var _seftVal = '';
		var _targetVal = '';
		for(var i=0;i<checkArr.length;i++){
			if(checkArr[i].checked){
				var name_id = checkArr[i].value.split(";");
				_seftVal += name_id[0]+";";
				_targetVal += name_id[1]+";";
			}
		}
		_seft.val(_seftVal);
		targetValId.val(_targetVal);
		targetId.hide();
	});

	targetId.find("#selectSub :checkbox").click(function(){		
		if(obj.mutil=='true'){
			if($(this).attr("checked"))
				$(this).attr("checked",false);
			else
				$(this).attr("checked",true);
		}else{
			targetId.find(":checkbox").attr("checked",false);
			$(this).attr("checked",true);
			var name_id = $(this).val().split(";"); 
			_seft.val( name_id[0]);
			targetValId.val(name_id[1]);
			targetId.hide();
		}
	});

	$(document).click(function(event){
		if(event.target.id!=_seft.selector.substring(1)){
			targetId.hide();	
		}
	});

	targetId.click(function(e){
		e.stopPropagation(); //  2
	});

    return _seft;
}
function demo1(){
	var array = [{name:'北京',id:'1'},{name:'上海',id:'2'},{name:'南京',id:'3'},{name:'杭州',id:'4'}];
	return array;
}

function getTemplate(array,obj){
	var loopHtml = "";
	for(var i = 0 ;i<array.length;i++){
		loopHtml +='<input type="checkbox" name="cr'+array[i].id+'"  id="cr'+array[i].id+'" value="'+array[i].name+';'+array[i].id+'"/><label for="cr'+array[i].id+'">'+array[i].name+'</label>';
		if(obj.countbr!=null)
		if((i+1)%obj.countbr==0)
			loopHtml +='<br>';
	}
	var template = '<div id="selectItem" style="width:'+obj.c_width+'; height:'+obj.c_height+'" class="selectItemhidden"> '+
					'<div id="selectItemAd" class="selectItemtit bgc_ccc" style="background:'+obj.tbakcolor+'"> '+
						'<h2 id="selectItemTitle" class="selectItemleft">'+(obj.title!=null?obj.title:"标题")+'</h2> '+
						'<div id="selectItemClose" class="selectItemright">关闭</div>'+
						'<div id="selectItemSubmit" class="selectItemright" style="display:'+((obj.mutil=="true")?"block":"none")+'">确定</div>'+
					'</div> '+
					'<div id="selectItemCount" class="selectItemcont">'+
					'<div id="selectSub">'+
					loopHtml+
					'</div> '+
					'</div> '+
					'</div> '; 
	return template;
}

function getCSS(){
	$("#selectItem").css({"background":"#FFF","position":"absolute","top":"0px","left":"center","border":"1px solid #000","overflow":"hidden","width":"240px","z-index":"1000"});
	$("body").css({"font-size":"12px"});
	$(".selectItemcont").css({"padding":"8px"});
	$(".selectItemtit").css({"line-height":"20px","height":"20px","margin":"1px","padding-left":"12px"});
	$(".bgc_ccc").css({"background":"#E88E22"});
	$(".selectItemleft").css({"float":"left","margin":"0px","padding":"0px","font-size":"12px","font-weight":"bold","color":"#fff"});
	$(".selectItemright").css({"float":"right","cursor":"pointer","color":"#fff","margin":"2px"});
	$(".selectItemcls").css({"clear":"both","font-size":"0px","height":"0px","overflow":"hidden"});
	$(".selectItemhidden").css({"display":"none"});
}

function divAttr(_div){
	this.countbr=_div.attr("countbr");
	this.c_width=_div.attr("c_width");
	this.c_height=_div.attr("c_height");
	this.tbakcolor=_div.attr("tbakcolor");
	this.mutil=_div.attr("mutil");
	this.title=_div.attr("title");
	this.data=_div.attr("data");
	this.demo=_div.attr("demo");
	return this;
}

function selectDownMenu(targetVals,targetIds,div){
	_div = $('#'+div);
	var obj = divAttr(_div);
	var array = obj.data;
	if(obj.demo=="true")
		array = demo1();
	else if(array=null){
		alert('您还没有选项值！');
		return;
	}
	_div.html(getTemplate(array,obj));
	getCSS();
	selectDown($('#'+targetVals),$('#'+targetIds),obj);
}
