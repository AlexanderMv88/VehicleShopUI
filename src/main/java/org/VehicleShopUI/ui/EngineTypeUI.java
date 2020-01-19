package org.VehicleShopUI.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.VehicleShopUI.entity.EngineType;
import org.VehicleShopUI.service.EngineTypeService;

import java.util.Optional;

@Route
public class EngineTypeUI extends VerticalLayout {

    private EngineTypeService engineTypeService;

    public EngineTypeUI(EngineTypeService engineTypeService) {
        this.engineTypeService = engineTypeService;
        Grid<EngineType> engineTypeGrid = new Grid<>(EngineType.class, true);
        TextField titleTField = new TextField("Тип");
        engineTypeGrid.addSelectionListener(selection->{
            Optional<EngineType> mayBeEngineType = selection.getFirstSelectedItem();
            if (mayBeEngineType.isPresent()){
                EngineType engineType = mayBeEngineType.get();
                titleTField.setValue(engineType.getEngineTypeTitle());
            }
        });

        engineTypeGrid.setItems(engineTypeService.getAll());


        Button saveBtn = new Button("Сохранить", e->{
            saveEngineType(engineTypeGrid, titleTField.getValue());
        });
        Button addBtn = new Button("Добавить", e->{
            addEngineType(engineTypeGrid, titleTField.getValue());
        });
        Button deleteBtn = new Button("Удалить", e->{
            removeEngineType(engineTypeGrid);
        });

        HorizontalLayout btnLayout = new HorizontalLayout(saveBtn, addBtn, deleteBtn);

        this.add(engineTypeGrid, titleTField, btnLayout);
    }

    private void removeEngineType(Grid<EngineType> engineTypeGrid) {
        EngineType engineType = getObjectSelectedInGrid(engineTypeGrid);
        engineTypeService.remove(engineType);
        Notification notification = new Notification(
                "Удалена запись: "+engineType.toString(), 3000);
        notification.open();
        //Обновляю Grid
        //TODO: Подумать как подтягивать обновленные записи
        engineTypeGrid.setItems(engineTypeService.getAll());
    }

    private void addEngineType(Grid<EngineType> engineTypeGrid, String engineTypeTitle) {
        EngineType engineType = new EngineType();
        engineType.setEngineTypeTitle(engineTypeTitle);
        //TODO: Получить Id с backendа
        EngineType engineTypeFromAPI = engineTypeService.create(engineType);
        Notification notification = new Notification(
                "Добавлена запись: "+engineTypeFromAPI.toString(), 3000);
        notification.open();
        //Обновляю Grid
        //TODO: Подумать как подтягивать обновленные записи
        engineTypeGrid.setItems(engineTypeService.getAll());
    }


    private void saveEngineType(Grid<EngineType> engineTypeGrid, String engineTypeTitle) {
        EngineType oldEngineType = getObjectSelectedInGrid(engineTypeGrid);
        EngineType engineType = new EngineType();
        engineType.setEngineTypeId(oldEngineType.getEngineTypeId());
        engineType.setEngineTypeTitle(engineTypeTitle);

        engineTypeService.save(engineType);
        Notification notification = new Notification(
                "Запись: "+oldEngineType.toString()+" изменена на "+engineType.toString(), 3000);
        notification.open();
        //Обновляю Grid
        //TODO: Подумать как подтягивать обновленные записи
        engineTypeGrid.setItems(engineTypeService.getAll());
    }

    private EngineType getObjectSelectedInGrid(Grid<EngineType> engineTypeGrid) {
        Optional<EngineType> firstSelectedItem = engineTypeGrid.asSingleSelect().getOptionalValue();
        if (firstSelectedItem.isPresent()) {
            //Получаю id выбранного объекта
            EngineType engineTypeFromListById = engineTypeService.getEngineTypeById(firstSelectedItem);
            if (engineTypeFromListById != null) {
                return engineTypeFromListById;
            }else{
                return engineTypeService.getEngineTypeByTitle(firstSelectedItem);
            }
        }
        return null;

    }


}
