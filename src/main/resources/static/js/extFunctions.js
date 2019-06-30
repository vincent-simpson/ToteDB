var rh = rh || {};
rh.mq = rh.mq || {};

$(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        if (options.type === "POST") {
            xhr.setRequestHeader(header, token);
        }
    });
});

rh.mq.attachEventHandlers = function() {
	$("#addBettingAreaForm").on('shown.bs.modal', function() {
		$("#defaultForm-betting-area").focus();

	});

	$("#modalAddMachineForm").on('shown.bs.modal', function() {
		$("#addMachine-lsnNumber").focus();

	});

};

$(document).ready(function() {
	rh.mq.attachEventHandlers();

});

function openBettingAreaModal(id) {
	$.ajax({
		type : 'POST',
		url : "/bettingAreas/edit?bettingAreaId=" + id,
		success : function(data) {
			$("#bettingAreaModalHolder").html(data);

			$("#bettingAreaModalHolder #add-bettingArea-button").html("Save");
			$("#bettingAreaModalHolder #add-bettingArea-title").html(
					"Edit Betting Area");

			$("#addBettingAreaForm").modal("show");

		}
	});
}

function bindParentButtonText(id) {

	if (id == 0) {
		id = -1;
	}

	// var id = -1;

	$.ajax({
		type : 'POST',
		url : "/bettingAreas/bindToModel?bettingAreaId=" + id,
		success : function(data) {
		}

	});
}

function openMachineModal(id) {
	$.ajax({
		type : 'POST',
		url : "/machines/edit?machineId=" + id,
		success : function(data) {
			$("#machineModalHolder").html(data);

			$("#machineModalHolder #add-machine-button").html("Save");
			$("#machineModalHolder #add-machine-title").html("Edit Machine");

			$("#modalAddMachineForm").modal("show");

		}
	});
}

function openNotesModal(id) {

	$.ajax({
		type : 'POST',
		url : '/machines/addNotes/bind?machineId=' + id,
		success : function(data) {
			$('#notesModalHolder').html(data);

			$("#modalAddNotesForm").modal("show");
		},

		error : function(data) {
			console.log(data);
		}

	});
}

function deleteBettingArea(id) {
	if (window.confirm("Are you sure?")) {

		// need to check if notes input and date haven't been edited. if so,
		// delete without sending ajax request.

		$.ajax({
			type : 'GET',
			url : "/bettingAreas/delete?bettingAreaId=" + id,
			success : function(result) {

				alert('Deleted!');
				window.location = '/bettingAreas/list';
			},
			error : function(result) {
				alert('Cannot delete!');
			}
		});

	}
}

function addRow(isSubmittedRow, date, note) {
	var empTab = document.getElementById("notesTable");

	if (!isSubmittedRow) {
		var tr = empTab.insertRow(-1); // TABLE ROW.
		var rowCnt = empTab.rows.length; // GET TABLE ROW COUNT.
		var colCnt = 3;

		for (var c = 0; c < colCnt; c++) {
			var td = document.createElement('td'); // TABLE DEFINITION.
			td = tr.insertCell(c);

			if (c == 0) { // FIRST COLUMN.
				// ADD A BUTTON.
				var removeButton = document.createElement('input');

				// SET INPUT ATTRIBUTE.
				removeButton.setAttribute('type', 'button');
				removeButton.setAttribute('value', 'Remove');
				removeButton.setAttribute('class', 'btn btn-primary btn-sm');
				removeButton.setAttribute('style', 'margin-bottom : 5px;');

				// ADD THE BUTTON's 'onclick' EVENT.
				removeButton.setAttribute('onclick', 'removeRow(this)');

				td.appendChild(removeButton);

				var saveButton = document.createElement('a');
				saveButton.setAttribute('class', 'btn btn-primary btn-sm');
				saveButton.setAttribute('style',
						'color : white; cursor : pointer;');
				saveButton.setAttribute('onclick', 'submit()');
				saveButton.setAttribute('id', 'notes-save-button');

				var v = document.createTextNode('Save');
				saveButton.appendChild(v);

				td.appendChild(saveButton);
			} else {
				// CREATE AND ADD TEXTBOX IN EACH CELL.
				switch (c) {
				case 1:
					var blockOfHtml = document.getElementById('blockOfHtml');
					if (blockOfHtml == null) {
						blockOfHtml = document.createElement('div');
						blockOfHtml.innerHtml = "<div id='blockOfHtml' style='display: none;'>";
					}

					var x;

					var today = new Date();
					var dd = today.getDate();
					var mm = today.getMonth() + 1;
					var yyyy = today.getFullYear();

					if (mm < 10) {
						mm = "0" + mm;
					}

					var todayFormatted = mm + '-' + dd + '-' + yyyy;

					x = '<div class="form-group row"> <div class="col-10"> <input class="form-control" type="text" id="notes-date-input" value="'
							+ todayFormatted
							+ '">'
							+ '<small id="date-label">Enter as Month/Day/Year</small></div> </div>';

					blockOfHtml.innerHTML = x;

					blockOfHtml.setAttribute('style', 'hidden : false;');

					td.appendChild(blockOfHtml);

					break;
				case 2:
					var ele;
					ele = document.createElement('input');
					ele.setAttribute('type', 'text');
					ele.setAttribute('value', '');
					ele.setAttribute('style', 'width : 100%;');
					ele.setAttribute('id', 'notes-add-new-input');
					td.appendChild(ele);
					break;

				}
			}
		}

	} else {
		$("#notes-date-input").replaceWith(
				"<td style='border-style: hidden;' id='notes-date-input'>"
						+ date + "</td>");
		$("#date-label").remove();
		$("#notes-save-button").remove();

		$("#notes-add-new-input").replaceWith(
				"<td style='border-style: hidden;' id='notes-add-new-input'>"
						+ note + "</td>");

	}

}

function removeRow(oButton, id) {
	var empTab = document.getElementById('notesTable');
	empTab.deleteRow(oButton.parentNode.parentNode.rowIndex);

	var delId = id;

	if (delId == null) { // this means that the thymeleaf id passed is not
		// valid. Meaning the row was generated by a submit
		// action
		delId = $("#note-id-holder").val();
	}

	$.ajax({
		type : 'POST',
		url : '/machines/deleteNotes?noteId=' + delId,
		success : function(data) {
		},
		error : function(data) {
			alert('Cannot delete note!');
		}

	});
}

// EXTRACT AND SUBMIT TABLE DATA.
function submit() {
	var myTab = document.getElementById('notesTable');
	var values = [];

	// LOOP THROUGH EACH ROW OF THE TABLE.

	var t = document.getElementById('notes-date-input');
	values.push("" + t.value.trim() + "");
	console.log(t.value.trim());

	var notes = document.getElementById('notes-add-new-input');
	values.push("" + notes.value + "");

	var mId = document.getElementById('notes-modal-machine-id').getAttribute(
			'value');

	$.ajax({
		type : 'POST',
		url : '/machines/addNotes?date=' + values[0] + '&note=' + values[1]
				+ '&machineId=' + mId,
		dataType : 'json',
		success : function(data) {
			var data_json = JSON.stringify(data);

			var data_formatted = {
				date : data[0],
				note : data[1],
				noteId : data[2]
			};

			addRow(true, data_formatted.date, data_formatted.note);
			$("#note-id-holder").val(data_formatted.noteId);

		},
		error : function(data) {
			alert('Notes cannot be empty!');
		}

	});

}

function createEmptyNotesTable() {
	var arrHead = [];
	arrHead = [ '', 'Date', 'Note' ];

	var emptyTable = document.createElement('table');
	emptyTable.setAttribute('id', 'notesTable');

	var tr = emptyTable.insertRow(-1);

	for (var h = 0; h < arrHead.length; h++) {
		var th = document.createElement('th'); // TABLE HEADER.
		th.innerHTML = arrHead[h];
		tr.appendChild(th);
	}

	var div = document.getElementById('cont');
	div.appendChild(emptyTable);
}

function addNewNoteButtonClicked() {
	var date = document.getElementById('notes-date-input');

	function checkValue(str, max) {
	  if (str.charAt(0) !== '0' || str == '00') {
	    var num = parseInt(str);
	    if (isNaN(num) || num <= 0 || num > max) num = 1;
	    str = num > parseInt(max.toString().charAt(0)) && num.toString().length == 1 ? '0' + num : num.toString();
	  }
		return str;
	}
	date.addEventListener('input', function(e) {
	  this.type = 'text';
	  var input = this.value;
	  if (/\D\/$/.test(input)) input = input.substr(0, input.length - 3);
	  var values = input.split('/').map(function(v) {
	    return v.replace(/\D/g, '')
	  });
	  if (values[0]) values[0] = checkValue(values[0], 12);
	  if (values[1]) values[1] = checkValue(values[1], 31);
	  var output = values.map(function(v, i) {
	    return v.length == 2 && i < 2 ? v + ' / ' : v;
	  });
	  this.value = output.join('').substr(0, 14);
	});

	date.addEventListener('blur', function(e) {
	  this.type = 'text';
	  var input = this.value;
	  var values = input.split('/').map(function(v, i) {
	    return v.replace(/\D/g, '')
	  });
	  var output = '';
	  
	  if (values.length == 3) {
	    var year = values[2].length !== 4 ? parseInt(values[2]) + 2000 : parseInt(values[2]);
	    var month = parseInt(values[0]) - 1;
	    var day = parseInt(values[1]);
	    var d = new Date(year, month, day);
	    if (!isNaN(d)) {
	      var dates = [d.getMonth() + 1, d.getDate(), d.getFullYear()];
	      output = dates.map(function(v) {
	        v = v.toString();
	        return v.length == 1 ? '0' + v : v;
	      }).join(' / ');
	    }
	  }
	  this.value = output;
	});
}

