(function() {
    var tabs = [].slice.call(document.querySelectorAll('#myTab .tab'));
    var tabpanes = [].slice.call(document.querySelectorAll('#myTab .tabpane'));

    tabs.forEach(function(tab) {
        tab.addEventListener('click', function(e) {
            var target = this;
            tabs.forEach(function(tab, i) {
                if (target === tab) {
                    tab.classList.add('active');
                    tabpanes[i].classList.add('active');
                } else {
                    tab.classList.remove('active');
                    tabpanes[i].classList.remove('active');
                }
            });
        }, false);
    });
})();
