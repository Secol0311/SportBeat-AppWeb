console.log("Aplicación SportBeat cargada!");

// Función simple para confirmar eliminación
function confirmDelete(event) {
    if (!confirm('¿Estás seguro de que quieres eliminar esto?')) {
        event.preventDefault();
    }
}