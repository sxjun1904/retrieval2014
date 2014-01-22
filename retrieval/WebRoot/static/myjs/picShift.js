function getTemplate(array,opt){
	var focus_width=opt.width;
	var focus_height=opt.height;
	var text_height=0;
	var swf_height = focus_height+text_height;
	var pics='',links='', texts='';
	for(var i=0 ;i<array.length;i++){
		pics+="|"+array[i].pic;
		links+="|"+array[i].link;
		texts+="|"+array[i].text;
	}
	
	pics=pics.substring(1);links=links.substring(1);texts=texts.substring(1);

	var template = '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="'+ focus_width +'" height="'+ swf_height +'">'
	+'<param name="allowScriptAccess" value="sameDomain">'
	+'<param name="movie" value="../myjs/img/focus.swf">'
	+'<param name="quality" value="high">'
	+'<param name="bgcolor" value="#FFFFFF">'
	+'<param name="menu" value="false">'
	+'<param name=wmode value="opaque">'
	+'<param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'">'	
	+'<embed src="../myjs/img/focus.swf" wmode="opaque" FlashVars="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'" menu="false" bgcolor="#ffffff" quality="high" width="'+ focus_width +'" height="'+ swf_height +'" allowScriptAccess="sameDomain" type="application/x-shockwave-flash"/>' 
	+'</object>';
	return template;
}

function demo1(){
	return [{pic:'../myjs/img/img01.jpg',link:'#',text:'郑州豫甲商贸一企业慰问驻三亚某基地官兵'},
	        {pic:'../myjs/img/img02.jpg',link:'#',text:'原副省长、省人大副主任李志斌品鉴水晶舍得'},
	        {pic:'../myjs/img/img03.jpg',link:'#',text:'西沙徐司令'}];
}

function findVal(v1,v2){
	return (v1!=null&&v1!='')?v1:v2;
}

function getDefalut(opt){
	this.data = findVal(opt.data,"");
	this.width = findVal(opt.width,360);
	this.height = findVal(opt.height,270);
	this.demo = findVal(opt.demo,false);
	return this;
}

function getPicShift(div,opt){
	opt=getDefalut(opt);
	if(opt.demo)
		opt.data = demo1();
	$('#'+div).html(getTemplate(opt.data,opt));
}