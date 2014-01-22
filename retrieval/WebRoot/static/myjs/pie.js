/*
 * title:正标题   
 * c_width:界面宽 800
 * c_height:界面高 400
 * background-color:背景色 
 * filltext:左上方文字说明
 * data:[{name,value,color},{name,value,color}]
 * demo:是否为demo true和false
 */
function pie2D_1(_id){
	var obj = $("#"+_id+"");
	
		var data = obj.attr("data");;
		if(obj.attr("demo")!=null&&obj.attr("demo")=="true"){
			data = demo();
		}else{
			if(data==null||data.length==0){
				alert("没有配置属性:data");
				return;
			}else{
				for(var i=0;i<data.length;i++)
					if(data[i].color==null||data[i].color=='')
						data[i].color = getRandomColor();
			}
		}
		
		var chart = new iChart.Pie2D({
			render : _id,
			data: data,
			title : findVal(obj.attr('title'),'动画饼图'),
			legend : {
			enable : true
		},
		sub_option : {
		label : {
			background_color:findVal(obj.attr('background-color'),null),
			sign:false,//设置禁用label的小图标
			padding:'0 4',
			border:{
			enable:false,
			color:'#666666'
		},
		fontsize:11,
		fontweight:600,
		color : '#4572a7'
		},
		border : {
			width : 2,
			color : '#ffffff'
		}
		},
		/*legend:{
			enable:true,
			padding:0,
			offsetx:findVal(obj.attr("width")/10,60),
			offsety:50,
			color:'#3e576f',
			fontsize:20,//文本大小
			sign_size:20,//小图标大小
			line_height:28,//设置行高
			sign_space:10,//小图标与文本间距
			border:false,
			align:'left',
			background_color : null//透明背景
		}, */
		animation:true,
		showpercent:true,
		decimalsnum:2,
		width : findVal(obj.attr("c_width"),800),
		height : findVal(obj.attr("c_height"),400),
		radius:140
		});
		//利用自定义组件构造右侧说明文本
		chart.plugin(new iChart.Custom({
			drawFn:function(){
				//在右侧的位置，渲染说明文字
				chart.target.textAlign('start')
				.textBaseline('top')
				.textFont('400 20px Verdana')
				.fillText(findVal(obj.attr('filltext'),''),findVal(obj.attr("width")/10,60),40,false,getRandomColor(),false,24)
				.textFont('400 12px Verdana');
				//.fillText('Source:ComScore,2012',120,160,false,'#999999');
			}
		})); 
		chart.draw(); 
}

function getRandomColor(){
	  return  '#' +
	    (function(color){
	    return (color +=  '0123456789abcdef'[Math.floor(Math.random()*16)])
	      && (color.length == 6) ?  color : arguments.callee(color);
	  })('');
}

function findVal(v1,v2){
	return (v1!=null&&v1!='')?v1:v2;
}

function demo(){
	var data = [
				{name : '其他',value : 0.73,color:'#6f83a5'},
				{name : '中国',value : 35.75,color:'#a5c2d5'},
				{name : '美国',value : 29.84,color:'#cbab4f'},
				{name : '日本',value : 24.88,color:'#76a871'},
				{name : '韩国',value : 6.77,color:'#9f7961'},
				{name : '朝鲜',value : 2.02,color:'#a56f8f'}
				];
	return data;
}