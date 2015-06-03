(function() {
	/*************** freezed codes begin ***************/
	var fileEle = document.getElementById('file'),
		previewEle = document.getElementById('preview'),
		metaEle = document.getElementById('meta'),
		viewEle = document.getElementById('view'),
		historyEle = document.getElementById('history'),
		clearEle = document.getElementById('clear');

	function preview(file) {
		if (!file) {
			metaEle.innerHTML = '';
			viewEle.innerHTML = '';
			alert('please select a file');
			return;
		}

		StorageUtil.addPreview(file);
		updateHistory();

		previewMeta(file);
		previewContent(file);
	}

	clearEle.addEventListener('click', function() {
		StorageUtil.clearPreviews();
		updateHistory();
	}, false);

	function updateHistory() {
		var previews = StorageUtil.loadPreviews();
		var table = ['<table>', '<thead><tr><th>', ['Name', 'Size', 'Type', 'Last modified date'].join('</th><th>'),
			'</th></tr></thead>', '<tbody>',
			previews.map(function(p) {
				return ['<tr><td>', [p.name, p.size, p.type, p.lastModifiedDate].join('</td><td>'),
					'</td></tr>'
				].join('');
			}).join(''),
			'</tbody>', '</table>'
		].join('');
		historyEle.innerHTML = table;
	}

	/*************** freezed codes end ***************/

	/*************** Part A begins ***************/
	previewEle.addEventListener('click', function() {
		// your codes here
	}, false);

	viewEle.addEventListener('dragover', function(e) {
		// your codes here
	}, false);

	viewEle.addEventListener('dragend', function(e) {
		// your codes here
	}, false);

	viewEle.addEventListener('drop', function(e) {
		// your codes here
	}, false);

	function previewMeta(file) {
		// your codes here
	}

	function previewContent(file) {
		// your codes here
	}

	/*************** Part B begins ***************/
	StorageUtil = {
		addPreview: function(file) {
			// your codes here
		},

		loadPreviews: function() {
			// your codes here
			// using default return value to avoid console error
			// you should change the return value to make things go well
			return [];
		},

		clearPreviews: function() {
			// your codes here
		}
	};
	updateHistory();
})();