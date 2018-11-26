package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by sebo on 11/20/18.
 * janko.sebastian@gmail.com
 */
public class UICreator {
    private MetadataRetriever mdr;
    private ListView<FactTable> factTablesListView;
    private TreeView<Attribute> attributesTree;
    private TreeView<Dimension> dimensionsTree;
    private ArrayList<Dimension> selectedDimensions;
    private ArrayList<Attribute> selectedAttributes;
    private TableView<ObservableList<String>> table;
    private String sqlStatement;

    UICreator(Stage stage) {
        mdr = new MetadataRetriever();
        factTablesListView = getFactTablesList();
        attributesTree = getAttributesTree();
        dimensionsTree = getDimensionsTree();
        selectedDimensions = new ArrayList<>();
        selectedAttributes = new ArrayList<>();
        table = new TableView<>();
        createUI(stage);
    }

    private void createUI(Stage stage) {
        GridPane grid = new GridPane();

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(30);
        grid.getColumnConstraints().add(column);

        column = new ColumnConstraints();
        column.setPercentWidth(70);
        grid.getColumnConstraints().add(column);


        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));


        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);
        factTablesListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    setDimensionsTree(newValue);
                    setAttributesTree(newValue);
                    selectedAttributes.clear();
                    selectedDimensions.clear();
                }
        );

        TextArea sqlStatementLbl = new TextArea("");
        sqlStatementLbl.setEditable(false);
        sqlStatementLbl.setWrapText(true);
        sqlStatementLbl.setMaxHeight(Double.MAX_VALUE);
        sqlStatementLbl.setMaxWidth(Double.MAX_VALUE);

        Button runBtn = new Button("Play");

        runBtn.setOnAction(event -> {
            sqlStatement = SQLGenerator.generateSELECTStatement(selectedDimensions, selectedAttributes);
            sqlStatement += SQLGenerator.generateFROMStatement(selectedDimensions, selectedAttributes);
            sqlStatement += SQLGenerator.generateWHEREStatement(mdr, factTablesListView.getSelectionModel().getSelectedItem(), selectedDimensions, selectedAttributes);
            sqlStatement += SQLGenerator.generateGROUPBYStatement(selectedDimensions);
            sqlStatementLbl.setText(sqlStatement);
            ArrayList<ArrayList<String>> rows = DataRetriever.execQuery(sqlStatement, selectedDimensions, selectedAttributes);
            fillTable(rows, selectedDimensions, selectedAttributes);
        });

        grid.add(runBtn, 0, 0);
        grid.add(factTablesListView, 0, 1);
        grid.add(attributesTree, 0, 2);
        grid.add(dimensionsTree, 0, 3);
        grid.add(sqlStatementLbl, 1, 0, 1, 2);
        grid.add(table, 1, 2, 1, 2);


        Scene scene = new Scene(new VBox(), 1100, 1000);
        ((VBox) scene.getRoot()).getChildren().addAll(grid);
        stage.setScene(scene);
        stage.setTitle("Star Model Viewer");

        stage.show();
    }

    private void fillTable(ArrayList<ArrayList<String>> rows, ArrayList<Dimension> selectedDimensions, ArrayList<Attribute> selectedAttributes) {
        table.getItems().clear();
        table.getColumns().clear();

        ArrayList<TableColumn> columns = new ArrayList<>();
        ObservableList<ObservableList<String>> rowData = FXCollections.observableArrayList();

        int i = 0;
        for (Dimension d : selectedDimensions) {
            int curr = i;
            TableColumn<ObservableList<String>, String> tc = new TableColumn<>(d.getImeAtrib());
            tc.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().get(curr)));
            columns.add(tc);
            i++;
        }
        for (Attribute a : selectedAttributes) {
            int curr = i;
            TableColumn<ObservableList<String>, String> tc = new TableColumn<>(a.getImeAtrib());
            tc.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().get(curr)));
            columns.add(tc);
            i++;
        }

        //dodaj stupce u tablicu
        for (TableColumn tc : columns) {
            table.getColumns().add(tc);
        }

        for (ArrayList<String> row : rows) {
            ObservableList<String> newRow = FXCollections.observableArrayList();
            newRow.addAll(row);
            rowData.add(newRow);
        }
        table.getItems().addAll(rowData);

    }

    private ListView<FactTable> getFactTablesList() {
        ListView<FactTable> listView = new ListView<>();
        ObservableList<FactTable> itemsList = FXCollections.observableArrayList();
        ArrayList<FactTable> factTables = mdr.getFactTables();

        itemsList.addAll(factTables);
        listView.setItems(itemsList);

        return listView;
    }

    private TreeView<Attribute> getAttributesTree() {
        CheckBoxTreeItem<Attribute> rootItem =
                new CheckBoxTreeItem<>(new Attribute("Mjere"));
        rootItem.setExpanded(true);

        final TreeView<Attribute> tree = new TreeView<>(rootItem);
        tree.setEditable(true);

        tree.setCellFactory(CheckBoxTreeCell.forTreeView());
        tree.setRoot(rootItem);
        tree.setShowRoot(true);
        return tree;
    }

    private void setAttributesTree(FactTable ft) {
        attributesTree.getRoot().getChildren().clear();
        ArrayList<Attribute> attributes = mdr.getAttributes(ft);
        Attribute rootAt = new Attribute("Mjere");

        attributesTree.getRoot().setValue(rootAt);

        for (Attribute at : attributes) {
            final CheckBoxTreeItem<Attribute> checkBoxTreeItem =
                    new CheckBoxTreeItem<>(at);

            checkBoxTreeItem.selectedProperty().addListener((obs, oldVal, newVal) -> {
                System.out.println(checkBoxTreeItem.getValue() + " selection state: " + newVal);
                selectionChangedOnAttribute(checkBoxTreeItem, newVal);
            });

            attributesTree.getRoot().getChildren().add(checkBoxTreeItem);
        }
    }

    private TreeView<Dimension> getDimensionsTree() {
        CheckBoxTreeItem<Dimension> rootItem =
                new CheckBoxTreeItem<>(new Dimension("Dimenzije"));
        rootItem.setExpanded(true);

        final TreeView<Dimension> tree = new TreeView<>(rootItem);
        tree.setEditable(true);

        tree.setCellFactory(CheckBoxTreeCell.forTreeView());

        tree.setRoot(rootItem);
        tree.setShowRoot(true);
        return tree;
    }

    private void setDimensionsTree(FactTable ft) {
        dimensionsTree.getRoot().getChildren().clear();
        ArrayList<Dimension> dimensions = mdr.getDimensions(ft);
        dimensionsTree.getRoot().setValue(new Dimension("Dimenzije"));

        for (Dimension d : dimensions) {
            CheckBoxTreeItem<Dimension> dLeaf = new CheckBoxTreeItem<>(d);

            dLeaf.selectedProperty().addListener((obs, oldVal, newVal) -> {
                System.out.println(dLeaf.getValue() + " selection state: " + newVal);
                selectionChangedOnDimension(dLeaf, newVal);
            });

            boolean found = false;
            for (TreeItem<Dimension> depNode : dimensionsTree.getRoot().getChildren()) {
                if (depNode.getValue().getNazTablica().equals(d.getNazTablica())) {
                    depNode.getChildren().add(dLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                CheckBoxTreeItem<Dimension> dNode = new CheckBoxTreeItem<>(new Dimension(d.getNazTablica()));
                dimensionsTree.getRoot().getChildren().add(dNode);
                dNode.getChildren().add(dLeaf);
            }
        }
    }

    private void selectionChangedOnDimension(CheckBoxTreeItem<Dimension> treeItem, Boolean isChecked) {
        if (isChecked) {
            selectedDimensions.add(treeItem.getValue());
        } else {
            selectedDimensions.remove(treeItem.getValue());
        }
    }

    private void selectionChangedOnAttribute(CheckBoxTreeItem<Attribute> treeItem, Boolean isChecked) {
        if (isChecked) {
            selectedAttributes.add(treeItem.getValue());
        } else {
            selectedAttributes.remove(treeItem.getValue());
        }
    }

}
