package io.wabm.supermarket.protocol;

import io.wabm.supermarket.misc.pojo.Classification;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Created by MainasuK on 2016-11-16.
 *
 * Helper protocol for setup cell factory
 */
public interface CellFactorySetupCallbackProtocol<S, T> extends Callback<TableColumn<S, T>, TableCell<S, T>> {

}
