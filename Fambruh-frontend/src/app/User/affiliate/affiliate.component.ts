import { Component, ElementRef, OnInit, ViewEncapsulation } from '@angular/core';
import { RouterLink } from '@angular/router';

declare var $: any; // jQuery
declare var Vivus: any; // Vivus
declare var $f: any; // Froogaloop2
import WOW from 'wowjs';


@Component({
  selector: 'app-affiliate',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './affiliate.component.html',
  styleUrl: './affiliate.component.css'
})
export class AffiliateComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    $(window).on('load', () => {
      setTimeout(() => {
        $('#CJ-Overlay').fadeOut(800);
      }, 2500);
      setTimeout(() => {
        $('#loading, #loading2').fadeOut(400);
      }, 1500);
      setTimeout(() => {
        $('.overlay').fadeOut(800);
      }, 3000);
    });

    // Logo Resize
    let lastScrollTop = 0;
    const delta = 5;
    $(window).scroll(() => { // Remove the 'event' parameter
      const st = $(this).scrollTop();

      if (Math.abs(lastScrollTop - st) <= delta)
        return;

      if (st > lastScrollTop) {
        $('.logo').css({
          'max-width': '100px'
        });
        $('nav').css({
          'height': '45px',
          'padding': '8px 20px'
        });
        $('.navbar-nav > li > a').css({
          'padding': '20px 0px'
        });
        $('.navbar-brand').css({
          'max-height': '30px'
        });
      } else {
        // upscroll code
        $('.logo').css({
          'max-width': '175px'
        });

        $('nav').css({
          'height': '80px',
          'padding': '15px 20px'
        });
        $('.navbar-nav > li > a').css({
          'padding': '29.5px 0px'
        });
        $('.navbar-brand').css({
          'max-height': '60px'
        });
      }
      lastScrollTop = st;
    });

    // Brings in Navigation on Scroll
    $(window).scroll(() => {
      const distance = $('.subnav').offset().top - 400;
      const $window = $(window);

      if ($window.scrollTop() >= distance) {
        $('.navbar-nav, .scroll-top').addClass('block');
      } else {
        $('.navbar-nav, .scroll-top').removeClass('block');
      }
    });

    // Watch video click event
    $('.watch-video').click(() => {
      $('.hero .content').addClass('zoomOut');
      setTimeout(() => {
        $('.video-modal').css({
          'height': '100%'
        }).dequeue();
        $('video').css({
          'top': '100%'
        }).dequeue();
      }, 500);
    });

    // Vivus animation initialization
    const obt6 = new Vivus('ACI-icon', {
      type: 'delayed',
      duration: 200,
      pathTimingFunction: Vivus.EASE,
      start: 'autostart',
      dashGap: 8
    });

  // Check if not touch device, then initialize WOW.js
if (!$('html').hasClass('touch')) {
  new WOW().init(); // Remove the options object
}


    // Add your other JavaScript code here...
  }

}