$(function partA() {
    // Your codes here for part A
    var $tab = $(".tab");
    var $tabpane = $(".tabpane");
    $tab.click(function(){
    	for (var i = 0; i < $tab.length; i++) {
    		if (this == $tab[i]) {
    			$tab.attr("class", "tab");
    			$(this).attr("class", "tab active");
    			$tabpane.attr("class", "tabpane");
    			$tabpane.eq(i).attr("class", "tabpane active");
    		}
    	}
    });
});

/************************** spliter ****************************/

(function partB() {
    function EventEmitter() {
        // Your codes here for part B
        this.map = {};
    }

    EventEmitter.prototype.on = function(name, callback) {
        // Your codes here for part B
        if (!this.map[name]) {
        	this.map[name] = [];
        }
        this.map[name].push(callback);
        return this;
    };

    EventEmitter.prototype.emit = function(name, data) {
        // Your codes here for part B
        for (var i = 0; i < this.map[name].length; i++) {
        	this.map[name][i](data);
        }
    };

    /* the following are test codes, DO NOT modify them */

    console.log('%ctest info for part B', 'font-size:2em');

    var ee = new EventEmitter();
    ee.on('event1', function(data) {
        console.log('%c%s', 'color:green', 'from event1 handler1: ' + data);
    });
    try {
        ee.on('event1', function(data) {
                console.log('%c%s', 'color:green', 'from event1 handler2: ' + data);
            })
            .on('event2', function(data) {
                console.log('%c%s', 'color:green', 'from event2 handler1: ' + data);
            });
        console.log('%cchain method passed', 'color:green');
    } catch (err) {
        console.log('%cchain method failed', 'color:red');
    }
    ee.emit('event1', 'event1 passed');
    ee.emit('event2', 'event2 passed');
})();

/************************** spliter ****************************/

(function partC() {
    function currying(fn) {
        // Your codes here for part C
        var args = [].slice.call(arguments);
        if (args.length > fn.length) {
        	var innerArgs = [].slice.call(args, 1);
        	return fn.apply(null, innerArgs);
       	} else {
       		return function () {
       			var innerArgs = [].slice.call(arguments);
       			args = args.concat(innerArgs);
       			return currying.apply(null, args);				
       		}
       	}
    }

    /* the following are test codes, DO NOT modify them */

    console.log('%ctest info for part C', 'font-size:2em');

    function add5(a, b, c, d, e) {
        return a + b + c + d + e;
    }
	
    var testcase = ['currying(add5, 1, 2, 3, 4, 5)',
        'currying(add5, 1, 2, 3, 4)(5)',
        'currying(add5, 1, 2, 3)(4, 5)',
        'currying(add5, 1, 2)(3, 4, 5)',
        'currying(add5, 1)(2, 3, 4, 5)',
        'currying(add5)(1, 2, 3, 4, 5)',
        'currying(add5)(1, 2, 3)(4, 5)',
        'currying(add5)(1)(2)(3)(4)(5)'
    ];
    testcase.forEach(function(test) {
        try {
            var result = eval(test);
            if (result === 15) {
                console.log('%c%s passed', 'color:green', test);
            } else {
                console.log('%c%s failed', 'color:red', test);
            }
        } catch (err) {
            console.log('%c%s failed', 'color:red', test);
        }
    });
})();
