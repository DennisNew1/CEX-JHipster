<template>
  <div>
    <h2 id="page-heading" data-cy="ScreenHeading">
      <span v-text="t$('jhipsterApp.screen.home.title')" id="screen-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.screen.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ScreenCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-screen"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.screen.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && screens && screens.length === 0">
      <span v-text="t$('jhipsterApp.screen.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="screens && screens.length > 0">
      <table class="table table-striped" aria-describedby="screens">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.screen.screenID')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.screen.name')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.screen.totalSeats')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.screen.movie')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="screen in screens" :key="screen.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ScreenView', params: { screenId: screen.id } }">{{ screen.id }}</router-link>
            </td>
            <td>{{ screen.screenID }}</td>
            <td>{{ screen.name }}</td>
            <td>{{ screen.totalSeats }}</td>
            <td>
              <div v-if="screen.movie">
                <router-link :to="{ name: 'MovieView', params: { movieId: screen.movie.id } }">{{ screen.movie.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ScreenView', params: { screenId: screen.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ScreenEdit', params: { screenId: screen.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(screen)"
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
        <span id="jhipsterApp.screen.delete.question" data-cy="screenDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-screen-heading" v-text="t$('jhipsterApp.screen.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-screen"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeScreen()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./screen.component.ts"></script>
