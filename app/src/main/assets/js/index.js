$(function(){
/* 点赞，废弃不用了，经供参考
	var a=false
	function compare(className,ite){
		if (a==false) {
			var now=$(className).text();
			now++;
			$(className).text(now);
			ite.attr("src","state-thumbs-heart.svg");
			a=true;
		} else{
			var now=$(className).text();
			now--;
			$(className).text(now);
			ite.attr("src","state-thumbs-up.svg");
			a=false;
		}
	}
	function oclick(ite,num){
		ite.click(function(){
			compare(num,ite);
			window.weichat.showToast("点赞+1");
		})
	}

	//oclick($(".item01 .heart")," .num");
	//oclick($(".item02 .heart"),".num02");
	//oclick($(".item03 .heart"),".num03");
	//oclick($(".item04 .heart"),".num04");
	//oclick($(".item05 .heart"),".num05");
	//oclick($(".item06 .heart"),".num06");
	//oclick($(".item07 .heart"),".num07");
*/
	//屏蔽状态
	$(".text_boxs").on('click', '.dele', function(){
	//$(".dele").click(function(){
		$(this).parents(".item").css("display","none");
	});
	//点击更多
	$(".down").click(function(){
		$(this).next($(".nexts_pl")).slideDown();
		$(this).fadeOut(0);
	})
	$(".up").click(function(){
		$(this).parent($(".nexts_pl")).slideUp();
		$(this).parent().prev($(".down")).delay(200).fadeIn(0);
	})
	//点击加载更多
	$(".end").click(function(){
    	window.weichat.showToast("努力加载中");
    	$(".text_boxs").append(window.weichat.getComment());
    })
	//点击评论argument
	$(".text_boxs").on('click', '.argument', function(){
	//$(".argument").click(function(){// 解决动态添加之后的js事件不响应，绑定在text_boxs上
    		var comment = prompt("","加油");
    		var userName = "null";
    		//window.weichat.showToast("评论："+ nowCommentElement);
    		//添加评论
    		/* 格式如下，追加在<texts_box>后面
    		<div class="pls">
                <p><span>秋香</span>：赞</p>
                <p><span>我爱保时捷</span>：滴滴，让出行更美丽！</p>
             </div>
    		*/
    		if(comment!=null)
                $(this).parents(".texts_box").append("<div class='pls'><p><span>"+userName+"</span>："+comment+"</p></div>");
            //window.weichat.showToast("评论："+ nowCommentElement);
    	})
/*
//    $(".heart").click(function(){
//            if(count != 0){
//                flag = false;
//            }
//            //获取当前点击的元素的className
//            var nowCommentElement = event.target.parentNode.parentNode.className;
//            // 判断用户第几次点击
//            if(flag == false){
//                var num = $("."+nowCommentElement+" .pl .num").text();
//                //换图片
//                $("."+nowCommentElement+" .pl .heart").attr("src","state-thumbs-heart.svg");
//                num++;
//                count++;
//                $("."+nowCommentElement+" .pl .num").text(num);
//                flag = true;
//                window.weichat.showToast("点赞+1");
//            }else{
//            // 和上面相反操作
//                var num = $("."+nowCommentElement+" .pl .num").text();
//                $("."+nowCommentElement+" .pl .heart").attr("src","state-thumbs-up.svg");
//                num--;
//                $("."+nowCommentElement+" .pl .num").text(num);
//                window.weichat.showToast("点赞-1");
//                flag = false;
//            }
//    })
*/
    //点赞事件，一人只能一次
    $(".text_boxs").on('click', '.heart', function(){
            // 一个用户只能点赞一次
            var temp = $(this).attr("src");
            if(temp.indexOf("up")>=0){
                //var num = $("."+nowLikeElement+" .pl .num").text();
                var num = $(this).next(".num").text();
                 //window.weichat.showToast(nowLikeElement);
                //换图片
                $(this).attr("src","state-thumbs-heart.svg");
                //数量加
                num++;
                $(this).next(".num").text(num);
            }else{
            // 和上面相反操作
                var num = $(this).next(".num").text();
                $(this).attr("src","state-thumbs-up.svg");
                num--;
                $(this).next(".num").text(num);
              //  window.weichat.showToast(num);
            }
    })
	//img_box
	function boxStyle(oname){
		var counts=$(oname).find("img");
		if (counts.length==1) {
			$(oname).css({"width":"10rem","overflow":"hidden"})
		    $(oname).find("img").css({"width":"10rem","height":"10rem"})
		}else if(counts.length==2){
			$(oname).css({"width":"19rem","overflow":"hidden"})
		    $(oname).find("img").css({"width":"8rem","height":"8rem","float":"left","marginBottom":"0.2rem","marginRight":"0.2rem"})
		}else if(counts.length==3||counts.length<=9&&counts.length>=5){
			$(oname).css({"width":"19rem","overflow":"hidden"})
		    $(oname).find("img").css({"width":"5rem","height":"5rem","float":"left","marginBottom":"0.2rem","marginRight":"0.2rem"})
		}else if(counts.length==4){
			$(oname).css({"width":"19rem","overflow":"hidden"})
		    $(oname).find("img").css({"width":"8rem","height":"8rem","float":"left","marginBottom":"0.2rem","marginRight":"0.2rem"})
		}
	}
	boxStyle(".img_box ");
	boxStyle(".img_box02 ");
	boxStyle(".img_box03 ");
	boxStyle(".img_box04 ");
	boxStyle(".img_box05 ");
})
