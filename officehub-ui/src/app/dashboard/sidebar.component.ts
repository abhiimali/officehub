import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  templateUrl: 'sidebar.component.html',
  styleUrls: ['sidebar.component.scss'],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class SidebarComponent {
  collapsed = false;

  tools = [
    { label: 'Dashboard', icon: 'dashboard', route: '/dashboard' },
    { label: 'Asset Hub', icon: 'inventory_2', route: '/dashboard/asset-hub' },
    { label: 'Meeting Hub', icon: 'video_call', route: '/dashboard/meeting-hub' },
    { label: 'Projects', icon: 'folder', route: '/projects' },
    { label: 'Reports', icon: 'bar_chart', route: '/reports' },
    { label: 'Users', icon: 'groups', route: '/users' }
  ];

  shortcuts = [
    { icon: 'settings', route: '/settings', label: 'Settings' },
    { icon: 'help_outline', route: '/help', label: 'Help' },
    { icon: 'smart_toy', route: '/chatbot', label: 'Chatbot' }
  ];

  toggleSidebar() {
    this.collapsed = !this.collapsed;
  }
}
