import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface Asset {
  id: string;
  name: string;
  category: string;
  serialNumber: string;
  assignedDate: Date;
  status: 'active' | 'pending' | 'returned';
}

interface AssetRequest {
  id: string;
  assetType: string;
  requestDate: Date;
  status: 'pending' | 'approved' | 'rejected';
  reason: string;
}

@Component({
  selector: 'app-asset-hub',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './asset-hub.component.html',
  styleUrls: ['./asset-hub.component.scss']
})
export class AssetHubComponent {
  showRequestModal = false;
  selectedAssetType = '';

  // Mock data - will be replaced with API calls
  myAssets: Asset[] = [
    {
      id: '1',
      name: 'MacBook Pro 16"',
      category: 'Laptop',
      serialNumber: 'MBP-2024-001',
      assignedDate: new Date('2024-01-15'),
      status: 'active'
    },
    {
      id: '2',
      name: 'iPhone 15 Pro',
      category: 'Mobile',
      serialNumber: 'IPH-2024-042',
      assignedDate: new Date('2024-02-20'),
      status: 'active'
    },
    {
      id: '3',
      name: 'Dell Monitor 27"',
      category: 'Monitor',
      serialNumber: 'MON-2024-128',
      assignedDate: new Date('2024-01-15'),
      status: 'active'
    }
  ];

  myRequests: AssetRequest[] = [
    {
      id: '1',
      assetType: 'Wireless Mouse',
      requestDate: new Date('2024-11-25'),
      status: 'pending',
      reason: 'Current mouse is malfunctioning'
    }
  ];

  availableAssetTypes = [
    { name: 'Laptop', icon: 'laptop_mac' },
    { name: 'Mobile Phone', icon: 'smartphone' },
    { name: 'Monitor', icon: 'monitor' },
    { name: 'Keyboard', icon: 'keyboard' },
    { name: 'Mouse', icon: 'mouse' },
    { name: 'Headset', icon: 'headset' },
    { name: 'Desk', icon: 'desk' },
    { name: 'Chair', icon: 'chair' }
  ];

  constructor(private router: Router) {}

  goBackToTools() {
    this.router.navigate(['/dashboard']);
  }

  openRequestModal() {
    this.showRequestModal = true;
  }

  closeRequestModal() {
    this.showRequestModal = false;
    this.selectedAssetType = '';
  }

  submitRequest() {
    // TODO: Implement API call to submit request
    console.log('Submitting request for:', this.selectedAssetType);
    this.closeRequestModal();
  }

  getStatusClass(status: string): string {
    return `status-${status}`;
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('en-US', { 
      year: 'numeric', 
      month: 'short', 
      day: 'numeric' 
    });
  }
}
