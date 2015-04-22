// JavaScript Document
 var numSlides=0;
    var currentSlide=0;
    function initializeSlideShow(n){
      numSlides=n;
      showSlide(currentSlide,true);
    }
    function displaySlide(n){
      showSlide(currentSlide,false);
      currentSlide=n;
      showSlide(currentSlide,true);     
    }
    function showSlide(n,display){
      var slide=document.getElementById("slide"+n).style;
      if(display) slide.display="block";
      else slide.display="none";
    }
    function goFirst(){
    	displaySlide(0);
     //"first"按钮做了什么
    }
    function goLast(){
    	displaySlide(numSlides - 1);
      //"last"按钮做了什么
    }
    function goNext(){
    	if (currentSlide != numSlides - 1) {
    		displaySlide(currentSlide + 1);
    	}
      //"next"按钮做了什么
    }
    function goPrevious(){
    	if (currentSlide != 0) {
    		displaySlide(currentSlide - 1);
    	}
      //"previous"按钮做了什么
    }