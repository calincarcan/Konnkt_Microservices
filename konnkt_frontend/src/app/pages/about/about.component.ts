import { Component } from '@angular/core';

@Component({
    selector: 'app-about',
    standalone: false,
    templateUrl: './about.component.html',
    styleUrls: ['./about.component.scss']
})
export class AboutComponent {
    constructor() { }

    ngOnInit(): void {
        console.log('AboutComponent initialized');
    }
}