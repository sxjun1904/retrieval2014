/*
 * title:正标题   折线图（正标题）
 * subtitle:副标题 副标题
 * c_width:界面宽 800
 * c_height:界面高 400
 * x:x轴单位
 * y:y轴单位
 * background-color:背景色 #b4b4b4
 * line-width:折线粗细 2
 * xy:xy轴显示信息{x},{y}
 * x-data:x轴的值 [数，组] *
 * y-data:y轴的值 [{name,value,color,line_width}，{name,value,color,line_width}] 
 * demo:是否为demo true和false
 */
function line2D_1(_id){
	var obj = $("#"+_id+"");
	var labels = findVal(obj.attr("x-data"),[]);
	var data = findVal(obj.attr("y-data"),[]);
	if(obj.attr("demo")!=null&&obj.attr("demo")=="true"){
		var o = demo1();
		data=o.y;
		labels=o.x;
	}else{
		if(isLabelOrDataNull(labels,data))
			return;
		data = getLineColorAndLineWidth(data,obj);
	}
	var chart = new iChart.LineBasic2D({
		render : _id,
		data: data,
		align:'center',
		title : {
		text:findVal(obj.attr('title'),'折线图（正标题）')
		//font : '微软雅黑',
		//fontsize:24,
		//color:findVal(obj.attr('background-color'),'#b4b4b4')
	},
	subtitle : {
		text:findVal(obj.attr("subtitle"),'副标题')
		//font : '微软雅黑',
		//color:'#b4b4b4'
	},
	/*footnote : {
		text:'ichartjs.com',
		font : '微软雅黑',
		fontsize:11,
		fontweight:600,
		padding:'0 28',
		color:'#b4b4b4'
	},*/
	width : findVal(obj.attr("c_width"),800),
	height :  findVal(obj.attr("c_height"),400),
	shadow:true,
	shadow_color : '#202020',
	shadow_blur : 8,
	shadow_offsetx : 0,
	shadow_offsety : 0,
	background_color:findVal(obj.attr('background-color'),null),
	animation : true,//开启过渡动画
	animation_duration:600,//600ms完成动画
	tip:{
		enable:true,
		shadow:true,
		listeners:{
		//tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
		parseText:function(tip,name,value,text,i){
			var xy = obj.attr("xy");
			return getXY(xy,labels[i],name,value);
		}
	}
	},
	crosshair:{
		enable:true,
		line_color:'#ec4646'
	},
	sub_option : {
		smooth : true,
		label:false,
		hollow:false,
		hollow_inside:false,
		point_size:8
	},
	coordinate:{
		width:640,
		height:260,
		striped_factor : 0.18,
		grid_color:'#4e4e4e',
		axis:{
		color:'#252525',
		width:[0,0,4,4]
	},
	scale:[{
		position:'left',
		start_scale:0,
		end_scale:100,
		scale_space:10,
		scale_size:2,
		scale_enable : false,
		label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600},
		scale_color:'#9f9f9f'
	},{
	position:'bottom',
	label : {color:'#9d987a',font : '微软雅黑',fontsize:11,fontweight:600},
		scale_enable : false,
		labels:labels
	}]
	}
	});
	//利用自定义组件构造左侧说明文本
	chart.plugin(new iChart.Custom({
	drawFn:function(){
		//计算位置
		var coo = chart.getCoordinate(),
		x = coo.get('originx'),
		y = coo.get('originy'),
		w = coo.width,
		h = coo.height;
		//在左上侧的位置，渲染一个单位的文字
		chart.target.textAlign('start')
		.textBaseline('bottom')
		.textFont('600 11px 微软雅黑')
		.fillText(findVal(obj.attr("y"),'y'),x-40,y-12,false,'#9d987a')
		.textBaseline('top')
		.fillText(findVal(obj.attr("x"),'x'),x+w+12,y+h+10,false,'#9d987a');
	}
	}));
	//开始画图
	chart.draw();
}

/*
 * title:正标题   
 * c_width:界面宽 800
 * c_height:界面高 400
 * background-color:背景色
 * x:x轴单位
 * y:y轴单位
 * xy:xy轴显示信息{x},{y}
 * line-width:线粗
 * x-data:x轴的值 [数，组] 
 * y-data:y轴的值 [{name,value,color,line_width}，{name,value,color,line_width}] 
 * demo:是否为demo true和false
 */
function area2D_1(_id){
	var obj = $("#"+_id+"");
	var labels = findVal(obj.attr("x-data"),[]);
	var data = findVal(obj.attr("y-data"),[]);
	if(obj.attr("demo")!=null&&obj.attr("demo")=="true"){
		var o = demo1();
		data=o.y;
		labels=o.x;
	}else{
		if(isLabelOrDataNull(labels,data))
			return;
		data = getLineColorAndLineWidth(data,obj);
	}
	var chart = new iChart.Area2D({
			render : _id,
			data: data,
			title : findVal(obj.attr("title"),'多点重合面积图'),
			subtitle : findVal(obj.attr("subtitle"),'副标题'),
			width : findVal(obj.attr("c_width"),800),
			height : findVal(obj.attr("c_height"),400),
			area_opacity:0.15,
			legend : {
				enable : true
			},
			tip:{
				enable : true,
				listeners:{
					//tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
					parseText:function(tip,name,value,text,i){
						var xy = obj.attr("xy");
						return getXY(xy,labels[i],name,value);
					}
				}
			},
			sub_option:{
				label:false
			},
			crosshair:{
				enable:true,
				line_color:'#62bce9'
			},
			coordinate:{
				axis : {
					width : [0, 0, 2, 0]
				},
				background_color:findVal(obj.attr("background-color"),'#ffffff'),
				height:'90%',
				valid_width:'94%',
				height : 260,
				scale2grid:false,
				grids:{
				horizontal:{
					way:'share_alike',
					value:8
				}
			},
			scale:[{
				position:'left',
				start_scale:0,
				end_scale:40,
				scale_space:5,
				listeners:{
				parseText:function(t,x,y){
				return {text:findVal(obj.attr("y"),t)}
			}
			}
			},{
				position:'bottom',
				start_scale:1,
				end_scale:12,
				parseText:function(t,x,y){
				return {textY:findVal(obj.attr("y"),y)}
			},
			labels:labels
			}]
			}
		});
		chart.draw(); 
}

/**
 * 判断x-data和y-data是否为空
 * @param labels
 * @param data
 * @returns {Boolean}
 */
function isLabelOrDataNull(labels,data){
	if(labels==null||labels.length==0){
		alert("没有配置属性:x-data");
		return true;
	}else if(data==null||data.length<=0){
		alert("没有配置属性:y-data");
		return true;
	}
}

/**
 *生成线条的颜色和粗细 
 * @param data
 * @param obj
 * @returns
 */
function getLineColorAndLineWidth(data,obj){
	for(var i=0;i<data.length;i++){
		if(data[i].color==null||data[i].color=='')
			data[i].color = getRandomColor();
		if(data[i].line_width==null||data[i].line_width==''){
			data[i].line_width = findVal(obj.attr("line-width"),2);
		}
	}
	return data;
}

/**
 * 得到xy轴焦点的值
 * @param xy
 * @param label
 * @param name
 * @param value
 * @returns {String}
 */
function getXY(xy,label,name,value){
	if(xy!=null){
		xy = xy.replace("{x}",label).replace("{n}",name).replace("{y}",value);
	}else{
		xy="x轴:"+findVal(label,'空')+";name:"+findVal(name,'空')+";y轴:"+findVal(value,'空');
	}
	return "<span style='color:#005268;font-size:12px;'>"+xy+"</span>";
}

/**
 * 获取随机颜色
 * @returns {String}
 */
function getRandomColor(){
	  return  '#' +
	    (function(color){
	    return (color +=  '0123456789abcdef'[Math.floor(Math.random()*16)])
	      && (color.length == 6) ?  color : arguments.callee(color);
	  })('');
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

/**
 * demo示例1
 * @returns {___o0}
 */
function demo1(){
	var o = new Object();
	var pv=[],ip=[],t;
	for(var i=0;i<12;i++){
	t = Math.floor(Math.random()*(30+((i%12)*5)))+10;
	pv.push(t);
	t = Math.floor(t*0.5);
	t = t-Math.floor((Math.random()*t)/2);
	ip.push(t);
	}
	var data = [
		{
			name : '数据1',
			value:pv,
			color:getRandomColor(),
			line_width:2
		},
		{
			name : '数据2',
			value:ip,
			color:getRandomColor(),
			line_width:2
		}
	];
	var labels = ["8-01","8-02","8-03","8-04","8-05","8-06","8-07","8-07","8-08","8-9","8-10","8-11"]; 
	o.y=data;
	o.x=labels;
	return o;
}