var App = new Ext.Application({ 
    name : 'NotesApp', 
    useLoadMask : true, 
    launch : function () { 
		 NotesApp.views.viewport = new Ext.Panel({ 
			fullscreen: true, 
			html:'This is the viewport' 
		}); 
    } 
}) 
