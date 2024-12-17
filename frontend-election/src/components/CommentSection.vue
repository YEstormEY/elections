<script lang="ts">
import {defineComponent} from 'vue';
import {deleteComment, editComment, fetchCommentsByArticleId, postComment} from "@/service/CommentService";
import {authState} from "@/utils/auth";
import {getCookie} from "@/utils/cookie/GetCookie";

export default defineComponent({
  name: 'Comments-component',
  computed: {
    authState() {
      return authState
    }
  },
  props: {
    articleId: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      comments: [], // Array to hold the fetched comments
      userComments: [],
      newComment: '', // Data binding for the new comment input
      isEditing: false,
      editedComment: '',
    };
  },
  methods: {
    async getComments() {
      try {
        this.comments = await fetchCommentsByArticleId(this.articleId);
        this.checkUserComment();
      } catch (error) {
        console.error('Failed to fetch comments:', error.message);
      }
    },

    async submitNewComment() {
      if (!this.newComment.trim()) {
        alert('Please enter a comment');
        return;
      }

      try {
        const response = await postComment(this.articleId, this.newComment);

        if(response) {
          this.newComment = '';
          await this.getComments(); // Re-fetch comments after submitting a new comment
        }

      } catch (error) {
        console.error('Failed to submit comment:', error.message);
        alert('Failed to submit comment: ' + error.message);
      }
    },

    async deleteComment(commentId)  {
      try {
        const response = await deleteComment(commentId);

        if(response) {
          this.userComments = this.userComments.filter(comment => comment.commentId !== commentId);
          await this.getComments(); // Re-fetch comments after deleting a comment
        }

      } catch (error) {
        console.error('Failed to delete comment:', error.message);
        alert('Failed to delete comment: ' + error.message);
      }
    },

    checkUserComment() {
    const userId = getCookie('userId');

    for (let i = 0; i < this.comments.length; i++) {
      if (this.comments[i].userId == userId) {
        this.userComments.push(this.comments[i]);
      }
    }
  },

    async editComment(commentId) {

      if (this.editedComment === '') {
        alert('Please enter a comment');
        return;
      }

      try {
        const response = await editComment(commentId, this.editedComment);

        if(response) {
          this.editedComment = '';
          this.userComments = this.userComments.filter(comment => comment.commentId !== commentId);
          await this.getComments(); // Re-fetch comments after submitting a new comment
          this.isEditing = !this.isEditing;
        }
      } catch (error) {
        console.error('Failed to submit comment:', error.message);
        alert('Failed to submit comment: ' + error.message);
      }
    },

    edit() {
      this.isEditing = !this.isEditing;
      this.editedComment = this.userComments[0].content;
    }


  },
  mounted() {
    this.getComments(); // Fetch comments when the component is mounted
    this.checkUserComment();
  },
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
      <button class="btn btn-primary" @click="submitNewComment">Submit</button>
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
          <button class="btn btn-danger btn-sm" @click="deleteComment(comment.commentId)">Delete</button>
          <button class="btn btn-primary btn-sm" v-if="!isEditing" @click=edit>Edit</button>
          <button class="btn btn-primary btn-sm" v-if="isEditing" @click="editComment(comment.commentId)">Save</button>
          <button class="btn btn-primary btn-sm" v-if="isEditing" @click="isEditing = !isEditing">Cancel</button>
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

</style>
