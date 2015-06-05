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
		preview(fileEle.files[0]);
	}, false);

	viewEle.addEventListener('dragover', function(e) {
		e.stopPropagation();
		e.preventDefault();
		e.dataTransfer.dropEffect = 'copy';
	}, false);

	viewEle.addEventListener('dragend', function(e) {
		e.stopPropagation();
		e.preventDefault();
	}, false);

	viewEle.addEventListener('drop', function(e) {
		e.stopPropagation();
		e.preventDefault();
		var files = e.dataTransfer.files;
		preview(files[0]);
	}, false);

	function previewMeta(file) {
		metaEle.innerHTML = 'Name: ' + file.name + '<br>Size: ' + file.size + ' bytes<br>Type: ' + file.type + '<br>Last Modified Date: ' + file.lastModifiedDate;
	}

	function previewContent(file) {
		var reader = new FileReader();
		if (file.type.match('image.*')) {
      		reader.onload = function(e) {
        		viewEle.innerHTML = '<img src="' + e.target.result + '"/>';
      		};
      		reader.readAsDataURL(file);
		} else if (file.type.match('text.*')) {
			reader.onload = function(e) {
				viewEle.innerHTML = e.target.result;
			};
			reader.readAsText(file);
		} else {
			viewEle.innerHTML = 'Preview for ' + file.type + ' is not supported.';
		}
	}

	/*************** Part B begins ***************/
	StorageUtil = {
		addPreview: function(file) {
			var p = {
				name: file.name,
				size: file.size, 
				type: file.type, 
				lastModifiedDate: file.lastModifiedDate
			}
			var previews = StorageUtil.loadPreviews();
			previews[previews.length] = p;
			localStorage.setItem("history", JSON.stringify(previews));
		},

		loadPreviews: function() {
			if (localStorage["history"] == undefined) {
				var newHistory = [];
				localStorage.setItem("history", JSON.stringify(newHistory));
			}
			return JSON.parse(localStorage.getItem("history"));
		},

		clearPreviews: function() {
			localStorage.clear();
		}
	};
	updateHistory();
})();