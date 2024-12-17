<script lang="ts">
import {defineComponent} from 'vue';
import {deleteComment, editComment, fetchCommentsByArticleId, postComment} from "@/service/CommentService";
import {authState} from "@/utils/auth";
import {getCookie} from "@/utils/cookie/GetCookie";

interface Comment {
  commentId: number;
  userId: number;
  username: string;
  content: string;
  created_at: string; // Assuming this is the date field
}


/**
 * CommentSection component to display comments for an article and allow users to post new comments.
 * This component also allows users to delete their own comments.
 */
export default defineComponent({
  name: 'Comments-component',
  computed: {
    authState() {
      return authState
    }
  },
  // prop to receive the articleId from the parent component
  props: {
    articleId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      comments: [] as Comment[],        // Array of all comments
      userComments: [] as Comment[],    // User's comments
      newComment: '',                   // New comment input
      isEditing: false,                 // Edit mode toggle
      editedComment: '',                // Edited comment content
    };
  },
  methods: {
    /**
     * Fetch comments for the current article.
     */
    async getComments() {
      try {
        this.comments = await fetchCommentsByArticleId(this.articleId);
        this.checkUserComment();
      } catch (error) {
        if (error instanceof Error) {
          console.error('Failed to fetch comments:', error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },

    /**
     * Submit a new comment for the current article.
     */
    async submitNewComment() {
      if (!this.newComment.trim()) {
        alert('Please enter a comment');
        return;
      }

      try {
        const response = await postComment(this.articleId, this.newComment);

        if (response) {
          this.newComment = '';
          await this.getComments();
        }
      } catch (error) {
        if (error instanceof Error) {
          console.error('Failed to submit comment:', error.message);
          alert('Failed to submit comment: ' + error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },


    /**
     * Delete a comment by its ID.
     * @param commentId
     */
    async deleteComment(commentId: number) {
      try {
        const response = await deleteComment(commentId);

        if (response) {
          this.userComments = this.userComments.filter(
              (comment) => comment.commentId !== commentId
          );
          await this.getComments();
        }
      } catch (error) {
        if (error instanceof Error) {
          console.error('Failed to delete comment:', error.message);
          alert('Failed to delete comment: ' + error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },


    // Check if the user has commented on the article
    checkUserComment() {
      const userId = getCookie('userId');
      if (!userId) return;

      this.userComments = this.comments.filter(
          (comment) => comment.userId.toString() === userId
      );
    },

    /**
     * Edit a comment
     * @param commentId
     */
    async editComment(commentId: number) {
      if (this.editedComment.trim() === '') {
        alert('Please enter a comment');
        return;
      }

      try {
        const response = await editComment(commentId, this.editedComment);

        if (response) {
          this.editedComment = '';
          await this.getComments();
          this.isEditing = false;
        }
      } catch (error) {
        if (error instanceof Error) {
          console.error('Failed to submit comment:', error.message);
          alert('Failed to submit comment: ' + error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
  },

    // toggle edit mode
    edit() {
      this.isEditing = !this.isEditing;
      this.editedComment = this.userComments[0].content;
    }


  },
  // Fetch comments when the component is mounted
  mounted() {
    this.getComments(); // Fetch comments when the component is mounted
    this.checkUserComment();
  },
  // watch for changes in the articleId prop
  watch: {
    articleId() {
      this.getComments(); // Re-fetch comments when the articleId changes
      this.userComments = [];
    },
  },
});
</script>

<template>
  <div class="mt-5">
    <h4>Comments</h4>

    <!-- New Comment Form -->
    <div class="input-group mb-3" v-if="authState.isLoggedIn">
      <input
          type="text"
          class="form-control"
          placeholder="Add a comment..."
          v-model="newComment"
      />
      <button class="btn btn-green" @click="submitNewComment">Submit</button>
    </div>
    <h4 v-if="!authState.isLoggedIn" class="text-muted">Please login to comment</h4>

    <!-- User Comments List -->
    <div v-if="userComments.length > 0">
      <h5>Your Comments</h5>
      <ul class="list-group">
        <li class="list-group-item" v-for="(comment, index) in userComments" :key="index">
          <p class="mb-0">{{comment.username}}</p>
          <p class="mb-1">{{ comment.content }}</p>
          <input type="text" class="form-control" v-if="isEditing" v-model="editedComment" />
          <small class="text-muted">Posted on {{ new Date(comment.created_at).toLocaleDateString() }}</small>
          <br>
          <button class="btn btn-red btn-sm" @click="deleteComment(comment.commentId)">Delete</button>
          <button class="btn btn-green btn-sm" v-if="!isEditing" @click=edit>Edit</button>
          <button class="btn btn-green btn-sm" v-if="isEditing" @click="editComment(comment.commentId)">Save</button>
          <button class="btn btn-red btn-sm" v-if="isEditing" @click="isEditing = !isEditing">Cancel</button>
        </li>
      </ul>
    </div>

    <hr>

    <!-- Comments List -->
    <div v-if="comments.length > 0">
      <h5>All Comments</h5>
      <ul class="list-group">
        <li class="list-group-item" v-for="(comment, index) in comments" :key="index">
          <p class="mb-0">{{comment.username}}</p>
          <p class="mb-1">{{ comment.content }}</p>
          <small class="text-muted">Posted on {{ new Date(comment.created_at).toLocaleDateString() }}</small>
        </li>
      </ul>
    </div>
    <p v-else class="text-muted">No comments yet. Be the first to comment!</p>
  </div>
</template>

<style scoped>
.input-group {
  max-width: 700px;
}

.btn-green {
  background-color: #28a745;
  border: none;
  color: white;
}

.btn-red {
  background-color: orangered;
  color: white;
  border: none;
}

.btn-green:hover {
  background-color: #218838;
  color: white;
}

.btn-red:hover {
  background-color: darkred;
  color:  white;
}

</style>
