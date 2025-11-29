import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface Tool {
  id: string;
  name: string;
  description: string;
  icon: string;
  color: string;
  route: string;
  stats?: { label: string; value: string };
}

@Component({
  selector: 'app-tool-cards',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tool-cards.component.html',
  styleUrls: ['./tool-cards.component.scss']
})
export class ToolCardsComponent {
  tools: Tool[] = [
    {
      id: 'asset-hub',
      name: 'Asset Hub',
      description: 'Manage employee assets, inventory, and allocations',
      icon: 'inventory_2',
      color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
      route: '/dashboard/asset-hub'
    },
    {
      id: 'meeting-hub',
      name: 'Meeting Hub',
      description: 'Schedule and manage meetings efficiently',
      icon: 'video_call',
      color: 'linear-gradient(135deg, #43d9ad 0%, #4facfe 100%)',
      route: '/dashboard/meeting-hub'
    }
  ];

  constructor(private router: Router) {}

  navigateToTool(route: string) {
    this.router.navigate([route]);
  }
}
