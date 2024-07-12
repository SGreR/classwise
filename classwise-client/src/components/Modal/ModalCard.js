import React, {useState} from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import DeleteModal from "./DeleteModal";
import AddModal from "./AddModal";
import {capitalize} from "../../utils/utils";

const ModalCard = ({mode, type, id, onDeleteConfirmed, onSave}) => {
    const [modal, setModal] = useState(false);
    const [item, setItem] = useState(null)

    const toggle = () => setModal(!modal);

    const handleDelete = () => {
        toggle()
        onDeleteConfirmed()
    }

    const handleSave = () => {
        toggle()
        onSave(item)
    }

    const handleItemChange = (item) => {
        setItem(item)
    }

    return (
        <>
            {mode == "add" ?
                (
                    <Button color="primary" onClick={toggle}>+ Add {type}</Button>
                ) : (
                    <Button color="danger" size="sm" onClick={toggle}>Delete</Button>
                )
            }
            <Modal isOpen={modal} toggle={toggle}>
                <ModalHeader toggle={toggle}>{capitalize(mode) + " " + capitalize(type)}</ModalHeader>
                <ModalBody>
                    {mode == "add" ?
                        (
                            <AddModal onItemChange={handleItemChange} type={type}/>
                        ) : (
                            <DeleteModal type={type} id={id}/>
                        )
                    }
                </ModalBody>
                <ModalFooter>
                    {mode == "add" ?
                        (
                            <Button color="primary" onClick={handleSave}>Save</Button>
                        ) : (
                            <Button color="danger" onClick={handleDelete}>Delete</Button>
                        )
                    }
                    <Button color="secondary" onClick={toggle}>Cancel</Button>
                </ModalFooter>
            </Modal>
        </>
    );
}

export default ModalCard;