 
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
		if(obj.mutil=='true'|| obj.mutil==true){
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

function getTemplate(obj){
	var loopHtml = "";
	var array = obj.data;
	for(var i = 0 ;i<array.length;i++){
		loopHtml +='<input type="checkbox" name="cr'+array[i].id+'"  id="cr'+array[i].id+'" value="'+array[i].name+';'+array[i].id+'"/><label for="cr'+array[i].id+'">'+array[i].name+'</label>';
		if(obj.countbr!=null)
		if((i+1)%obj.countbr==0)
			loopHtml +='<br>';
	}
	var template = '<div id="selectItem" style="width:'+obj.width+'; height:'+obj.height+'" class="selectItemhidden"> '+
					'<div id="selectItemAd" class="selectItemtit bgc_ccc" style="background:'+obj.background+'"> '+
						'<span id="selectItemTitle" class="selectItemleft">'+obj.title+'</span> '+
						'<div id="selectItemClose" class="selectItemright">关闭</div>'+
						'<div id="selectItemSubmit" class="selectItemright" style="display:'+((obj.mutil=="true"|| obj.mutil==true)?"block":"none")+'">确定</div>'+
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
	//$(".bgc_ccc").css({"background":"#E88E22"});
	$(".selectItemleft").css({"float":"left","margin":"0px","padding":"0px","font-size":"12px","font-weight":"bold","color":"#fff"});
	$(".selectItemright").css({"float":"right","cursor":"pointer","color":"#fff","margin":"2px"});
	$(".selectItemcls").css({"clear":"both","font-size":"0px","height":"0px","overflow":"hidden"});
	$(".selectItemhidden").css({"display":"none"});
}

/**
 * 将v1的null或空转化为v2
 * @param v1
 * @param v2
 * @returns
 */
function findVal(v1,v2){
	return (v1!=null&&v1!='')?v1:v2;
}

function getDefalut(opt){
	this.countbr = findVal(opt.countbr,"4");
	this.width = findVal(opt.width,"100");
	this.height = findVal(opt.height,"60");
	this.background = findVal(opt.background,"#E88E22");
	this.mutil = findVal(opt.mutil,"false");
	this.title = findVal(opt.title,"标题");
	this.data = opt.data;
	this.demo = findVal(opt.demo,false);
	return this;
}

function selectDownMenu(targetVals,targetIds,div, opt){
	_div = $('#'+div);
	var opt = getDefalut(opt);
	if(opt.demo=="true"||opt.demo==true)
		opt.data = demo1();
	else if(opt.data==null){
		alert('您还没有选项值！');
		return;
	}
	_div.html(getTemplate(opt));
	getCSS();
	selectDown($('#'+targetVals),$('#'+targetIds),opt);
}
