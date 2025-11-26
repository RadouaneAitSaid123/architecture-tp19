import { Routes } from '@angular/router';
import { VoituresComponent } from './voitures/voitures.component';

export const routes: Routes = [
    { path: 'voitures', component: VoituresComponent },
    { path: '', redirectTo: '/voitures', pathMatch: 'full' }
];
