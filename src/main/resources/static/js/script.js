document.addEventListener('DOMContentLoaded', function() {
        // Remove session variable 'message' after a delay (e.g., 1 second)
        setTimeout(function() {
          fetch('/removeMessageFromSession'); 
          var alert = document.getElementById('alert'); 
          alert.classList.add("fade-out");
          setTimeout(function() {
            alert.remove(); // Remove the alert from the DOM after transition
          }, 1000); 
          // Remove the alert from the DOM
          console.log("popup removed");
        }, 1500); 
});