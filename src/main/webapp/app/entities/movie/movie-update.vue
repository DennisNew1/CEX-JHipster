<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.movie.home.createOrEditLabel"
          data-cy="MovieCreateUpdateHeading"
          v-text="t$('jhipsterApp.movie.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="movie.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="movie.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.movie.movieId')" for="movie-movieId"></label>
            <input
              type="text"
              class="form-control"
              name="movieId"
              id="movie-movieId"
              data-cy="movieId"
              :class="{ valid: !v$.movieId.$invalid, invalid: v$.movieId.$invalid }"
              v-model="v$.movieId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.movie.title')" for="movie-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="movie-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.movie.genre')" for="movie-genre"></label>
            <input
              type="text"
              class="form-control"
              name="genre"
              id="movie-genre"
              data-cy="genre"
              :class="{ valid: !v$.genre.$invalid, invalid: v$.genre.$invalid }"
              v-model="v$.genre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.movie.duration')" for="movie-duration"></label>
            <input
              type="number"
              class="form-control"
              name="duration"
              id="movie-duration"
              data-cy="duration"
              :class="{ valid: !v$.duration.$invalid, invalid: v$.duration.$invalid }"
              v-model.number="v$.duration.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.movie.releaseDate')" for="movie-releaseDate"></label>
            <div class="d-flex">
              <input
                id="movie-releaseDate"
                data-cy="releaseDate"
                type="datetime-local"
                class="form-control"
                name="releaseDate"
                :class="{ valid: !v$.releaseDate.$invalid, invalid: v$.releaseDate.$invalid }"
                :value="convertDateTimeFromServer(v$.releaseDate.$model)"
                @change="updateZonedDateTimeField('releaseDate', $event)"
              />
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./movie-update.component.ts"></script>
