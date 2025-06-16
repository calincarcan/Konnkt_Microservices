import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.interface';
import { UserService } from '../../services/user.service';

@Component({
    selector: 'app-users',
    standalone: false,
    templateUrl: './user.component.html',
    styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
    users: User[] = [];
    paginatedUsers: User[] = [];
    filteredUsers: User[] = [];
    loading: boolean = true;
    error: string | null = null;
    searchTerm: string = '';
    
    // Pagination properties
    currentPage: number = 1;
    pageSize: number = 4;
    totalPages: number = 0;

    constructor(private userService: UserService) { }

    ngOnInit(): void {
        this.loadUsers();
    }

    private loadUsers(): void {
        this.userService.getAllUsers().subscribe({
            next: (users) => {
                this.users = users;
                this.filteredUsers = this.users;
                this.totalPages = Math.ceil(this.filteredUsers.length / this.pageSize);
                this.updatePaginatedUsers();
                this.loading = false;
            },
            error: (err) => {
                this.error = 'Failed to load users';
                this.loading = false;
                console.error('Error loading users:', err);
            }
        });
    }

    onSearch(): void {
        if (!this.searchTerm) {
            this.filteredUsers = this.users;
        } else {
            this.filteredUsers = this.users.filter(user => 
                user.username.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
                user.email.toLowerCase().includes(this.searchTerm.toLowerCase())
            );
        }
        this.currentPage = 1;
        this.updatePaginatedUsers();
    }

    updatePaginatedUsers(): void {
        const startIndex = (this.currentPage - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        this.paginatedUsers = this.filteredUsers.slice(startIndex, endIndex);
        this.totalPages = Math.ceil(this.filteredUsers.length / this.pageSize);
    }

    changePage(page: number): void {
        this.currentPage = page;
        this.updatePaginatedUsers();
    }
}