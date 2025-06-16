import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { Post } from '../../models/post.interface';
import { Forum } from '../../models/forum.interface';
import { PostService } from '../../services/post.service';
import { ForumService } from '../../services/forum.service';

interface PostWithForum extends Post {
    forumName: string;
}

@Component({
    selector: 'app-home',
    standalone: false,
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    posts: PostWithForum[] = [];
    paginatedPosts: PostWithForum[] = [];
    loading: boolean = true;
    error: string | null = null;

    // Pagination properties
    currentPage: number = 1;
    pageSize: number = 5;
    totalPages: number = 0;

    constructor(
        private postService: PostService,
        private forumService: ForumService
    ) { }

    ngOnInit(): void {
        this.loadPosts();
    }    private loadPosts(): void {
        forkJoin({
            posts: this.postService.getAllPosts(),
            forums: this.forumService.getAllForums()
        }).subscribe({
            next: ({ posts, forums }) => {
                this.posts = posts
                    .map(post => ({
                        ...post,
                        forumName: forums.find(f => f.id === post.forumId)?.name || 'Unknown Forum'
                    }))
                    .sort((a, b) => b.score - a.score);
                this.updatePaginatedPosts();
                this.loading = false;
            },
            error: (err) => {
                this.error = 'Failed to load posts';
                this.loading = false;
                console.error('Error loading posts:', err);
            }
        });
    }

    updatePaginatedPosts(): void {
        const startIndex = (this.currentPage - 1) * this.pageSize;
        this.paginatedPosts = this.posts.slice(startIndex, startIndex + this.pageSize);
        this.totalPages = Math.ceil(this.posts.length / this.pageSize);
    }

    changePage(page: number): void {
        if (page >= 1 && page <= this.totalPages) {
            this.currentPage = page;
            this.updatePaginatedPosts();
        }
    }
}