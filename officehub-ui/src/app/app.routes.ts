
import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
	{
		path: 'dashboard',
		component: DashboardComponent,
		children: [
			{
				path: '',
				loadComponent: () => import('./dashboard/tool-cards.component').then(m => m.ToolCardsComponent)
			},
			{
				path: 'asset-hub',
				loadComponent: () => import('./dashboard/asset-hub/asset-hub.component').then(m => m.AssetHubComponent)
			},
			{
				path: 'meeting-hub',
				loadComponent: () => import('./dashboard/meeting-hub/meeting-hub.component').then(m => m.MeetingHubComponent)
			}
		]
	},
	{ path: '', redirectTo: '/dashboard', pathMatch: 'full' },
	{ path: '**', redirectTo: '/dashboard' }
];
