<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="movie">
        <h2 class="jh-entity-heading" data-cy="movieDetailsHeading">
          <span v-text="t$('jhipsterApp.movie.detail.title')"></span> {{ movie.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.movie.movieId')"></span>
          </dt>
          <dd>
            <span>{{ movie.movieId }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.movie.title')"></span>
          </dt>
          <dd>
            <span>{{ movie.title }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.movie.genre')"></span>
          </dt>
          <dd>
            <span>{{ movie.genre }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.movie.duration')"></span>
          </dt>
          <dd>
            <span>{{ movie.duration }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.movie.releaseDate')"></span>
          </dt>
          <dd>
            <span v-if="movie.releaseDate">{{ formatDateLong(movie.releaseDate) }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="movie.id" :to="{ name: 'MovieEdit', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./movie-details.component.ts"></script>
