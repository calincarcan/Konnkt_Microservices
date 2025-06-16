import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Post } from '../../models/post.interface';
import { PostService } from '../../services/post.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss'],
  standalone: false
})
export class PostsComponent implements OnInit {
  posts: Post[] = [];
  paginatedPosts: Post[] = [];
  filteredPosts: Post[] = [];
  loading: boolean = true;
  error: string | null = null;
  searchTerm: string = '';
  
  // Pagination properties
  currentPage: number = 1;
  pageSize: number = 4;
  totalPages: number = 0;

  createForm: FormGroup;
  editingPostTitle: Post | null = null;
  editingPostContent: Post | null = null;
  editTitleForm: FormGroup;
  editContentForm: FormGroup;

  constructor(
    private postService: PostService,
    private fb: FormBuilder
  ) {
    this.createForm = this.fb.group({
      title: ['', [Validators.required]],
      content: ['', [Validators.required]],
      forumId: [1, Validators.required]  // Setting default value to 1
    });

    this.editTitleForm = this.fb.group({
      title: ['', [Validators.required]]
    });

    this.editContentForm = this.fb.group({
      content: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadPosts();
  }

  private loadPosts(): void {
    this.postService.getAllPosts().subscribe({
      next: (posts) => {
        this.posts = posts;
        this.filteredPosts = this.posts;
        this.totalPages = Math.ceil(this.filteredPosts.length / this.pageSize);
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

  onSearch(): void {
    if (!this.searchTerm) {
      this.filteredPosts = this.posts;
    } else {
      this.filteredPosts = this.posts.filter(post =>
        post.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        post.content.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
    this.currentPage = 1;
    this.updatePaginatedPosts();
  }

  updatePaginatedPosts(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedPosts = this.filteredPosts.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.filteredPosts.length / this.pageSize);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePaginatedPosts();
    }
  }

  confirmDelete(post: Post): void {
    if (confirm(`Are you sure you want to delete the post "${post.title}"?`)) {
      this.deletePost(post);
    }
  }

  private deletePost(post: Post): void {
    this.postService.deletePost(post.id).subscribe({
        next: () => {
            this.posts = this.posts.filter(p => p.id !== post.id);
            this.updatePaginatedPosts();
            this.loadPosts();
        },
        error: (err) => {
            console.error('Error deleting post:', err);
            this.error = 'Failed to delete post';
        }
    });
  }

  onSubmit(): void {
    if (this.createForm.valid) {
      this.postService.createPost(this.createForm.value).subscribe({
          next: () => {
              this.loadPosts();
              this.createForm.reset();
          },
          error: (err) => {
              console.error('Error creating post:', err);
              this.error = 'Failed to create post';
          }
      });
    }
  }

  startEditingTitle(post: Post): void {
    this.editingPostTitle = post;
    this.editTitleForm.patchValue({
      title: post.title
    });
  }

  startEditingContent(post: Post): void {
    this.editingPostContent = post;
    this.editContentForm.patchValue({
      content: post.content
    });
  }

  cancelEditingTitle(): void {
    this.editingPostTitle = null;
    this.editTitleForm.reset();
  }

  cancelEditingContent(): void {
    this.editingPostContent = null;
    this.editContentForm.reset();
  }

  updatePostTitle(): void {
    if (!this.editingPostTitle || !this.editTitleForm.valid) return;

    const updatedTitle = this.editTitleForm.get('title')?.value;
    
    this.postService.updatePostTitle(this.editingPostTitle.id, updatedTitle).subscribe({
      next: (post) => {
        const index = this.posts.findIndex(p => p.id === post.id);
        if (index !== -1) {
          this.posts[index] = post;
          this.updatePaginatedPosts();
        }
        this.cancelEditingTitle();
      },
      error: (err) => {
        console.error('Error updating post title:', err);
        this.error = 'Failed to update post title';
      }
    });
  }

  updatePostContent(): void {
    if (!this.editingPostContent || !this.editContentForm.valid) return;

    const updatedContent = this.editContentForm.get('content')?.value;
    
    this.postService.updatePostContent(this.editingPostContent.id, updatedContent).subscribe({
      next: (post) => {
        const index = this.posts.findIndex(p => p.id === post.id);
        if (index !== -1) {
          this.posts[index] = post;
          this.updatePaginatedPosts();
        }
        this.cancelEditingContent();
      },
      error: (err) => {
        console.error('Error updating post content:', err);
        this.error = 'Failed to update post content';
      }
    });
  }
}