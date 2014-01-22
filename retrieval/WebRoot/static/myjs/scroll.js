function scroll(opt,obj,callback){
                //参数初始化
                if(!opt) var opt={};
                var _btnUp = $("#"+ opt.up);//Shawphy:向上按钮
                var _btnDown = $("#"+ opt.down);//Shawphy:向下按钮
                var timerID;
                var _this=obj.eq(0).find("ul:first");
                var     lineH=_this.find("li:first").height(), //获取行高
                        line=opt.line?parseInt(opt.line,10):parseInt(obj.height()/lineH,10), //每次滚动的行数，默认为一屏，即父容器高度
                        speed=opt.speed?parseInt(opt.speed,10):500; //卷动速度，数值越大，速度越慢（毫秒）
                        timer=opt.timer //?parseInt(opt.timer,10):3000; //滚动的时间间隔（毫秒）
                if(line==0) line=1;
                var upHeight=0-line*lineH;
                //滚动函数
                var scrollUp=function(){
                        _btnUp.unbind("click",scrollUp); //Shawphy:取消向上按钮的函数绑定
                        _this.animate({
                                marginTop:upHeight
                        },speed,function(){
                                for(i=1;i<=line;i++){
                                        _this.find("li:first").appendTo(_this);
                                }
                                _this.css({marginTop:0});
                                _btnUp.bind("click",scrollUp); //Shawphy:绑定向上按钮的点击事件
                        });

                }
                //Shawphy:向下翻页函数
                var scrollDown=function(){
                        _btnDown.unbind("click",scrollDown);
                        for(i=1;i<=line;i++){
                                _this.find("li:last").show().prependTo(_this);
                        }
                        _this.css({marginTop:upHeight});
                        _this.animate({
                                marginTop:0
                        },speed,function(){
                                _btnDown.bind("click",scrollDown);
                        });
                }
               //Shawphy:自动播放
                var autoPlay = function(){
                        if(timer)timerID = window.setInterval(scrollUp,timer);
                };
                var autoStop = function(){
                        if(timer)window.clearInterval(timerID);
                };
                 //鼠标事件绑定
                _this.hover(autoStop,autoPlay).mouseout();
                _btnUp.css("cursor","pointer").click( scrollUp ).hover(autoStop,autoPlay);//Shawphy:向上向下鼠标事件绑定
                _btnDown.css("cursor","pointer").click( scrollDown ).hover(autoStop,autoPlay);

        }

function getTemplate(opt){
	var array = opt.data;
	var loopHtml = "";
	for(var i = 0;i<array.length;i++){
		
		loopHtml +='<li>';
		if(array[i].category)
			loopHtml +='[<a href="'+array[i].category.link+'">'+array[i].category.title+'</a>] ';
		loopHtml +='<a href="'+array[i].content.link+'" title="'+array[i].content.title+'" class="linktit">'+array[i].content.title+'</a></li> ';
	}
	var template ='<div class="scrollbox"> '+
		'<div class="scroltit"><h3><a href="'+opt.title.link+'">'+opt.title.name+'</a></h3><small id="but_up">↑向上</small><small id="but_down">↓向下</small></div> '+
	    '<div id="scrollDiv"> '+
	        '<ul id="scrollul"> '
	    	+loopHtml+
	        '</ul> '+
	    '</div> '+
	'</div>';
	return template;
}

function demo1(){
	var array = [{category:{title:'网页特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页特效',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}},
	             {category:{title:'网页',link:'http://www.51xuediannao.com/js/texiao/'},content:{title:'为网站增加圣诞节祝福动画百度的圣诞老人动画',link:'http://www.51xuediannao.com/js/texiao/642.html'}}
	];
	return array;
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
	this.line = findVal(opt.line,"");
	this.speed = findVal(opt.speed,"");
	this.timer = findVal(opt.timer,"");
	this.up = findVal(opt.up,"");
	this.down = findVal(opt.down,"");
	this.evencolor = findVal(opt.evencolor,"");
	this.oddcolor = findVal(opt.oddcolor,"");
	this.title = findVal(opt.title,{name:'滚动示例',link:'#'});
	this.data = opt.data;
	this.demo = findVal(opt.demo,false);
	return this;
}

function getScroll(div,opt){
	var _div = $('#'+div);
	opt = getDefalut(opt);
	if(opt.demo){
		var o = demo1();
		opt={line:1,speed:500,timer:3000,up:"but_up",down:"but_down",data:o};
	}
	else if(opt.data=null){
		alert('您还没有选项值！');
		return;
	}
	_div.html(getTemplate(opt));
	if(opt.evencolor)
		$("#scrollul li:even").css({background:opt.evencolor});//奇数行  
	if(opt.oddcolor)
		$("#scrollul li:odd").css({background:opt.oddcolor});//偶数行
	scroll(opt,$("#scrollDiv"));
}