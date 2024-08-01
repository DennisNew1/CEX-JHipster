<template>
  <div>
    <h2 id="page-heading" data-cy="MovieHeading">
      <span v-text="t$('jhipsterApp.movie.home.title')" id="movie-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.movie.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MovieCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-movie"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.movie.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && movies && movies.length === 0">
      <span v-text="t$('jhipsterApp.movie.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="movies && movies.length > 0">
      <table class="table table-striped" aria-describedby="movies">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.movie.movieId')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.movie.title')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.movie.genre')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.movie.duration')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.movie.releaseDate')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="movie in movies" :key="movie.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MovieView', params: { movieId: movie.id } }">{{ movie.id }}</router-link>
            </td>
            <td>{{ movie.movieId }}</td>
            <td>{{ movie.title }}</td>
            <td>{{ movie.genre }}</td>
            <td>{{ movie.duration }}</td>
            <td>{{ formatDateShort(movie.releaseDate) || '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MovieView', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MovieEdit', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(movie)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="jhipsterApp.movie.delete.question" data-cy="movieDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-movie-heading" v-text="t$('jhipsterApp.movie.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-movie"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMovie()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./movie.component.ts"></script>
