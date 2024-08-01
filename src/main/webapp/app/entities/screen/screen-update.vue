<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterApp.screen.home.createOrEditLabel"
          data-cy="ScreenCreateUpdateHeading"
          v-text="t$('jhipsterApp.screen.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="screen.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="screen.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.screen.screenID')" for="screen-screenID"></label>
            <input
              type="text"
              class="form-control"
              name="screenID"
              id="screen-screenID"
              data-cy="screenID"
              :class="{ valid: !v$.screenID.$invalid, invalid: v$.screenID.$invalid }"
              v-model="v$.screenID.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.screen.name')" for="screen-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="screen-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.screen.totalSeats')" for="screen-totalSeats"></label>
            <input
              type="number"
              class="form-control"
              name="totalSeats"
              id="screen-totalSeats"
              data-cy="totalSeats"
              :class="{ valid: !v$.totalSeats.$invalid, invalid: v$.totalSeats.$invalid }"
              v-model.number="v$.totalSeats.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jhipsterApp.screen.movie')" for="screen-movie"></label>
            <select class="form-control" id="screen-movie" data-cy="movie" name="movie" v-model="screen.movie">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="screen.movie && movieOption.id === screen.movie.id ? screen.movie : movieOption"
                v-for="movieOption in movies"
                :key="movieOption.id"
              >
                {{ movieOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./screen-update.component.ts"></script>
