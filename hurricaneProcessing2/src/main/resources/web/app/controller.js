/**
 * Main application controller
 *
 * You can use this controller for your whole app if it is small
 * or you can have separate controllers for each logical section
 *
 */
;(function() {

  angular
    .module('boilerplate')
    .controller('MainController', MainController);

  MainController.$inject = ['LocalStorage', 'QueryService','$http'];


  function MainController(LocalStorage, QueryService,$http) {

    // 'controller as' syntax
    var self = this;

    self.hurricanes = [];
    self.place = "";

    self.loadPlace = function(){

      $http.get("/hurricanes?name=" + self.place)
          .then(function(response){
            self.hurricanes = response.data;
          })
          .catch(function(error){
            console.error(error);
          });
    };




    ////////////  function definitions


    /**
     * Load some data
     * @return {Object} Returned object
     */
    // QueryService.query('GET', 'posts', {}, {})
    //   .then(function(ovocie) {
    //     self.ovocie = ovocie.data;
    //   });
  }


})();
