document.addEventListener('DOMContentLoaded', function () {
    // Add your JavaScript code for securing the test interface here
    var fullscreenButton = document.getElementById('fullscreenButton');
    fullscreenButton.addEventListener('click', enterFullscreen);

    disableCopyPaste();
    preventTabSwitching();
    preventEscKeyPress();
});

function enterFullscreen() {
    var element = document.documentElement;

    if (element.requestFullscreen) {
        element.requestFullscreen();
    } else if (element.mozRequestFullScreen) {
        element.mozRequestFullScreen();
    } else if (element.webkitRequestFullscreen) {
        element.webkitRequestFullscreen();
    } else if (element.msRequestFullscreen) {
        element.msRequestFullscreen();
    }

    // Optionally, you can remove the button after entering fullscreen
    var fullscreenButton = document.getElementById('fullscreenButton');
    fullscreenButton.style.display = 'none';
}

function disableCopyPaste() {
    document.addEventListener('contextmenu', function (e) {
        e.preventDefault();
    });

    document.addEventListener('copy', function (e) {
        e.preventDefault();
    });

    document.addEventListener('cut', function (e) {
        e.preventDefault();
    });

    document.addEventListener('paste', function (e) {
        e.preventDefault();
    });
}

function preventTabSwitching() {
    document.addEventListener('visibilitychange', function () {
        if (document.hidden) {
            // Page is hidden, take action (e.g., show a warning)
            alert('Do not switch tabs during the test!');
        } else {
            // Page is visible again
        }
    });
}

function preventEscKeyPress() {
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') {
            e.preventDefault();
            // Handle ESC key press (e.g., show a warning)
            alert('Do not press ESC during the test!');
        }
    });
}
