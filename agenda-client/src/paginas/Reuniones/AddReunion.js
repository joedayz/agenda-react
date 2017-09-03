import React from 'react';
import Select from 'react-select';
import axios from 'axios';
import { Button, Modal, FormGroup, ControlLabel, FormControl } from 'react-bootstrap';

var AddReunion = React.createClass({

	getInitialState: function() {
		
		return {
			addObject: {
				id: '', 
				nombre: '',
				descripcion: '',
				departamentos: ''
			}
		}
    },
	
	render: function() {

		if(this.props.parent.state.showAddModal === false){
			return (<div></div>);
		}	
	
		return (
			<Modal show={this.props.parent.state.showAddModal}>
				<Modal.Header>
					<Modal.Title>Agregar Reunion</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<form>
						<FormGroup>
							<ControlLabel>Nombre Reunion</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese nombre"
								value={this.state.addObject.nombre}
								onChange={this.onAddMeetingNameChange} />
							<br />
							
							<ControlLabel>Descripcion</ControlLabel>
							<FormControl
								type="text"
								placeholder="Ingrese descripcion"
								value={this.state.addObject.descripcion}
								onChange={this.onAddReunionDescripcionChange} />
							<br />
							
							<ControlLabel>Departamentos</ControlLabel>
							<Select
								name="departamentosField"
								multi={true}
								value={this.props.parent.getDepartamentoOptions(this.state.addObject.departamentos)}
								options={this.props.parent.getDepartamentoOptions(this.props.parent.state.departamentos)}
								onChange={this.onAddReunionDepartamentoChange} />
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
	onAddReunionNombreChange: function(event) {
		this.state.addObject.nombre = event.target.value;
		this.forceUpdate();
	},

	onAddReunionDescripcionChange: function(event) {
		this.state.addObject.descripcion = event.target.value;
		this.forceUpdate();
	},

	onAddReunionDepartamentoChange: function(selection) {

		if (selection === null) {
			this.state.updateObject.departamentos = null;
		} else {
			var departamentos = selection.map(function(obj){
				var rObj = {};
				rObj['id'] = obj['value'];
				rObj['nombre'] = obj['label'];
				return rObj;
			});
			
			this.state.addObject.departamentos = departamentos;
		}
		
		this.forceUpdate();		
	},

	onAddBtnClicked: function() {

		//Save reunion
		axios.post('http://localhost:8080/reuniones/', this.state.addObject)
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

export default AddReunion;