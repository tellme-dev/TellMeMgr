
/**获得弹出层居中的方法**/
function getDialogPosition(wrap,offsize){
	var temp = $(wrap).css("width");
	var dl = $(document).scrollLeft(),
		dt = $(document).scrollTop(),
		ww = $(window).width(),
		wh = $(window).height(),
		ow = temp.substring(0,temp.indexOf("p")),
		oh = $(wrap).height(),
		left = (ww - ow) / 2 + dl,
		top = top = (oh < 4 * wh / 7 ? wh * 0.382 - oh / 2 : (wh - oh) / 2) + dt;
	
    left = Math.max(left, dl),
    top = Math.max(top, dt) - offsize;
    sc = new Array();
    sc[0] = top;
    sc[1] = left;
	    
	return sc;
}
