// Copyright 2010 htmldrive.net Inc.
/**
 * @projectHomepage: http://www.htmldrive.net/s/view/id/605
 * @projectDescription: Stylish featured image slideshow jQuery plugin.
 * @author htmldrive.net
 * More script and css style : htmldrive.net
 * @version 1.0
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
(function(a){
    a.fn.images_hover_move=function(p){
        var p=p||{};
        var window_width=p&&p.window_width?p.window_width:"600";
        var window_height=p&&p.window_height?p.window_height:"250";
        var border_color=p&&p.border_color?p.border_color:"#999";
        var background_color = p&&p.background_color?p.background_color:"#f5f5f5";
        var title_color = p&&p.title_color?p.title_color:"#333";
        var title_background_color = p&&p.title_background_color?p.title_background_color:"#CCC";
        window_width = parseInt(window_width);
        window_height = parseInt(window_height);
        var images_array = new Array();
        var title_array = new Array();
        var g=a(this);
        var current = -1;
        var y=g.children("ul").children("li").length;
        if(y==0){
            g.append("Require content");
            return null
        }
        var thumb_width = (window_width-2-8-(y-1)*4)/y;
        var thumb_height = window_height-2-8;
	        g.children("ul").children("li").each(function(i){
            images_array[i] = $(this).find("img").attr("src");
            title_array[i] = $(this).find("img").attr("title");
        });
        init();
        function init(){
            g.css('width',window_width+'px').css('height',window_height+'px')
            g.children("ul").css('border-color',border_color).css('width',(window_width-2)+'px').css('height',(window_height-2)+'px').css('background-color',background_color);
            g.children("ul").children('li').css('width',thumb_width+'px').css('height',thumb_height+'px');
            g.children("ul").children('li').children('a').css('width',thumb_width+'px').css('height',thumb_height+'px');
            g.children("ul").children('li').children('a').children('img').hide();
            g.children("ul").children("li").each(function(i){
                $(this).children('a').css('background-image','url('+images_array[i]+')');
                $(this).append('<p>'+title_array[i]+'</p>');
            });
            g.children("ul").children("li").children("p").css('width',thumb_width+'px').css('color',title_color).css('background-color',title_background_color);
            g.children("ul").children("li").children("a").show();
            g.children("ul").children("li").children("p").slideUp();
            g.children("ul").children("li").children("a").hover(
                function(){
					var image_width = parseInt($(this).children("img").width());
					var image_height = parseInt($(this).children("img").height());
                    $(this).animate( {backgroundPosition: -(image_width-thumb_width) } , 1000 );
                    $(this).parent().children("p").slideDown();
                },
                function(){
                    $(this).animate( {backgroundPosition: 0 } , 1000 );
                    $(this).parent().children("p").slideUp();
                }
            );
        }
    }
})(jQuery);