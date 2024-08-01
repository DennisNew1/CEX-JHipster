<template>
  <div class="home row">
    <div class="col-md-3">
      <span class="movie img-fluid">
        <img class="img-fluid movie" src="/content/images/homepageImage.jpg" />
      </span>
    </div>
    <div class="col-md-9">
      <h1 class="display-4" v-text="t$('home.title')"></h1>
      <p class="lead" v-text="t$('home.subtitle')"></p>

      <router-link to="/booking-ext">{{ t$('home.bookinglink') }}</router-link>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
