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

const toggleSidebar = () => {
    var sidebar = document.getElementById('sidebar');
    var content = document.getElementById('content');
    var displayStyle = window.getComputedStyle(sidebar).getPropertyValue('display');
    if(displayStyle == 'block') {
      // setTimeout(() =>{
      //   sidebar.classList.add("toggle-out");
      //   setTimeout(()=> {
          sidebar.style.display = 'none';
          content.style.setProperty('margin-left', '2%');
      //   }, 600); 
      // }, 900); 
    }
    else {
      // setTimeout(() =>{
      //   sidebar.classList.add("toggle-in");
      //   setTimeout(()=> {
          sidebar.style.display = 'block';
          content.style.setProperty('margin-left', '20%');
      //   }, 600); 
      // }, 900); 
      
    }
}