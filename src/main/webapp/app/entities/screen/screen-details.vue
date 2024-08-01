<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="screen">
        <h2 class="jh-entity-heading" data-cy="screenDetailsHeading">
          <span v-text="t$('jhipsterApp.screen.detail.title')"></span> {{ screen.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.screen.screenID')"></span>
          </dt>
          <dd>
            <span>{{ screen.screenID }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.screen.name')"></span>
          </dt>
          <dd>
            <span>{{ screen.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.screen.totalSeats')"></span>
          </dt>
          <dd>
            <span>{{ screen.totalSeats }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.screen.movie')"></span>
          </dt>
          <dd>
            <div v-if="screen.movie">
              <router-link :to="{ name: 'MovieView', params: { movieId: screen.movie.id } }">{{ screen.movie.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="screen.id" :to="{ name: 'ScreenEdit', params: { screenId: screen.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./screen-details.component.ts"></script>
