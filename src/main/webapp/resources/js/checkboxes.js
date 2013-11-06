$(document).ready( function() {
	var toomuch = false;
	var i = 0;
$( ".ui-chkbox-box" ).click(function() {

$( "tr[aria-selected='true'][role='row']" ).each(function() {
	i++;
});

if (i >= 2) {
	toomuch = true;
}
else {
	toomuch = false;
}
	
if (toomuch) {
	$( "tr[aria-selected='false'][role='row']" ).removeClass("ui-datatable-selectable");
	$( "tr[aria-selected='false'][role='row']" ).removeClass("ui-state-hover");
}
else {
	$( "tr[role='row']" ).addClass("ui-datatable-selectable");
}
i = 0;
});
});