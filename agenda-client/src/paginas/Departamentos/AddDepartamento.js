import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { Button, Modal, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';

var AddDepartamento = React.createClass({

	getInitialState: function() {

		return {
			addObject: {
				id: '', 
				nombre: '',
				descripcion: ''
			}
		}
    },

	render: function() {

		return (
			<Modal show={this.props.parent.state.showAddModal}>
				<Modal.Header>
					<Modal.Title>Agregar Departamento</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<form>
						<FormGroup>
							<ControlLabel>Nombre de Departamento</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese nombre"
								value={this.state.addObject.nombre}
								onChange={this.onAddDepartamentoNombreChange} />
							<br />
							
							<ControlLabel>Descripcion</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese Descripcion"
								value={this.state.addObject.descripcion}
								onChange={this.onAddDepartamentoDescripcionChange} />
							<br />
						</FormGroup>
					</form>						
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.parent.closeAddModal}>Cerrar</Button>
					<Button bsStyle="primary" onClick={this.onAddBtnClicked}>Agregar</Button>
				</Modal.Footer>				
			</Modal>
		);
	},

	clearAddObject: function() {

		this.state.addObject.id = '';
		this.state.addObject.nombre = '';
		this.state.addObject.descripcion = '';
	},

	//Input changes
	onAddDepartamentoNombreChange: function(event) {
		this.state.addObject.nombre = event.target.value;
		this.forceUpdate();
	},

	onAddDepartamentoDescripcionChange: function(event) {
		this.state.addObject.descripcion = event.target.value;
		this.forceUpdate();
	},
	
	onAddBtnClicked: function() {

		//Save departamento
		axios.post('http://localhost:8080/departamentos/', this.state.addObject)
			.then(function (response) {
				this.props.parent.closeAddModal();
				this.props.parent.refreshTable();
				console.log(response);
			}.bind(this))
			.catch(function (error) {
				console.log(error);
			});
	}
});

export default AddDepartamento;