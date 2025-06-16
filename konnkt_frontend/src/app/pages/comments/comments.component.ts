import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Comment } from '../../models/comment.interface';
import { CommentService } from '../../services/comment.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss'],
  standalone: false
})
export class CommentsComponent implements OnInit {
  comments: Comment[] = [];
  paginatedComments: Comment[] = [];
  filteredComments: Comment[] = [];
  loading: boolean = true;
  error: string | null = null;
  searchTerm: string = '';
  
  // Pagination properties
  currentPage: number = 1;
  pageSize: number = 4;
  totalPages: number = 0;

  createForm: FormGroup;
  editingComment: Comment | null = null;
  editForm: FormGroup;

  constructor(
    private commentService: CommentService,
    private fb: FormBuilder
  ) {
    this.createForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(1)]],
      postId: ['', Validators.required]
    });

    this.editForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  ngOnInit(): void {
    this.loadComments();
  }

  private loadComments(): void {
    this.commentService.getAllComments().subscribe({
      next: (comments) => {
        this.comments = comments;
        this.filteredComments = this.comments;
        this.totalPages = Math.ceil(this.filteredComments.length / this.pageSize);
        this.updatePaginatedComments();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load comments';
        this.loading = false;
        console.error('Error loading comments:', err);
      }
    });
  }

  onSearch(): void {
    if (!this.searchTerm) {
      this.filteredComments = this.comments;
    } else {
      this.filteredComments = this.comments.filter(comment =>
        comment.content.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
    this.currentPage = 1;
    this.updatePaginatedComments();
  }

  updatePaginatedComments(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedComments = this.filteredComments.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.filteredComments.length / this.pageSize);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePaginatedComments();
    }
  }

  confirmDelete(comment: Comment): void {
    if (confirm(`Are you sure you want to delete this comment?\n\n"${comment.content.slice(0, 100)}${comment.content.length > 100 ? '...' : ''}"?`)) {
      this.deleteComment(comment);
    }
  }

  private deleteComment(comment: Comment): void {
    this.commentService.deleteComment(comment.id).subscribe({
        next: () => {
            this.comments = this.comments.filter(c => c.id !== comment.id);
            this.updatePaginatedComments();
            this.loadComments();
        },
        error: (err) => {
            console.error('Error deleting comment:', err);
            this.error = 'Failed to delete comment';
        }
    });
  }

  onSubmit(): void {
    if (this.createForm.valid) {
      this.commentService.createComment(this.createForm.value).subscribe({
          next: () => {
              this.loadComments();
              this.createForm.reset();
          },
          error: (err) => {
              console.error('Error creating comment:', err);
              this.error = 'Failed to create comment';
          }
      });
    }
  }

  startEditing(comment: Comment): void {
    this.editingComment = comment;
    this.editForm.patchValue({
      content: comment.content
    });
  }

  cancelEditing(): void {
    this.editingComment = null;
    this.editForm.reset();
  }

  updateComment(): void {
    if (!this.editingComment || !this.editForm.valid) return;

    this.commentService.updateComment(this.editingComment.id, this.editForm.get('content')?.value)
      .subscribe({
        next: (comment) => {
          const index = this.comments.findIndex(c => c.id === comment.id);
          if (index !== -1) {
            this.comments[index] = comment;
            this.updatePaginatedComments();
          }
          this.cancelEditing();
        },
        error: (err) => {
          console.error('Error updating comment:', err);
          this.error = 'Failed to update comment';
        }
      });
  }
}
