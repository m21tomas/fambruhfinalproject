import { Component, ElementRef } from '@angular/core';

@Component({
  selector: 'app-landingvideo',
  standalone: true,
  imports: [],
  template:`
  <video id="video" class="fill" [src]="videoSource" [muted]="isMuted" loop autoplay (click)="toggleVolume()" (touchstart)="toggleVolume()"></video>
`,
  styles: [`
  .fill {
    width: 100%;
    height: 100vh;
    object-fit: fill;
  }
`]
})
export class LandingvideoComponent {
  video!: HTMLVideoElement;
  videoSource: string = 'assets/11mb.mp4'; // Default video source
  isMuted: boolean = true; // Initially mute the video

  constructor(private elementRef: ElementRef) {}

  ngAfterViewInit() {
    this.video = this.elementRef.nativeElement.querySelector('#video');
  }

  toggleVolume() {
    this.isMuted = !this.isMuted;
    this.video.muted = this.isMuted; // Toggle video muting
  }
}
