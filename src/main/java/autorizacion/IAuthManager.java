package autorizacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *IAuthManager interface is implemented by an auth manager application component.
 * An auth manager is mainly responsible for providing role-based access control (RBAC) service.
 * @author Código Lite
 */
public interface IAuthManager {
    
	/**
	 * Performs access check for the specified user.
	 * @param itemName String the name of the operation that we are checking access to
	 * @param userId Object the user ID. This should be either an integer or a string representing
	 * the unique identifier of a user. See {@link IWebUser::getId}.
	 * @param params HashMap name-value pairs that would be passed to biz rules associated
	 * with the tasks and roles assigned to the user.
	 * @return boolean whether the operations can be performed by the user.
	 */
    
	public boolean checkAccess(String itemName,Object userId,HashMap params);

	/**
	 * Creates an authorization item.
	 * An authorization item represents an action permission (e.g. creating a post).
	 * It has three types: operation, task and role.
	 * Authorization items form a hierarchy. Higher level items inheirt permissions representing
	 * by lower level items.
	 * @param name string the item name. This must be a unique identifier.
	 * @param type integer the item type (0: operation, 1: task, 2: role).
	 * @param description string description of the item
	 * @param bizRule string business rule associated with the item. This is a piece of
	 * PHP code that will be executed when {@link checkAccess} is called for the item.
	 * @param data object additional data associated with the item.
	 * @return Autorizacion the authorization item
	 * throws Exception if an item with the same name already exists
	 */
	public Autorizacion createAuthItem(String name,int type,String description,String bizRule,Object data);
	/**
	 * Removes the specified authorization item.
	 * @param name string the name of the item to be removed
	 * @return boolean whether the item exists in the storage and has been removed
	 */
	public boolean removeAuthItem(String name);
	/**
	 * Returns the authorization items of the specific type and user.
	 * @param type integer the item type (0: operation, 1: task, 2: role). Defaults to null,
	 * meaning returning all items regardless of their type.
	 * @param userId object the user ID. Defaults to null, meaning returning all items even if
	 * they are not assigned to a user.
	 * @return List<Autorizacion> the authorization items of the specific type.
	 */
	public ArrayList<Autorizacion> getAuthItems(Integer type,Object userId);
	/**
	 * Returns the authorization item with the specified name.
	 * @param name string the name of the item
	 * @return Autorizacion the authorization item. Null if the item cannot be found.
	 */
	public Autorizacion getAuthItem(String name);
	/**
	 * Saves an authorization item to persistent storage.
	 * @param item Autorizacion the item to be saved.
	 * @param oldName string the old item name. If null, it means the item name is not changed.
	 */
	public void saveAuthItem(Autorizacion item,String oldName);

	/**
	 * Adds an item as a child of another item.
	 * @param itemName string the parent item name
	 * @param childName string the child item name
	 * throws CException if either parent or child doesn't exist or if a loop has been detected.
	 */
	public boolean addItemChild(String itemName,String childName);
	/**
	 * Removes a child from its parent.
	 * Note, the child item is not deleted. Only the parent-child relationship is removed.
	 * @param itemName string the parent item name
	 * @param childName string the child item name
	 * @return boolean whether the removal is successful
	 */
	public boolean removeItemChild(String itemName,String childName);
	/**
	 * Returns a value indicating whether a child exists within a parent.
	 * @param itemName string the parent item name
	 * @param childName string the child item name
	 * @return boolean whether the child exists
	 */
	public boolean hasItemChild(String itemName,String childName);
	/**
	 * Returns the children of the specified item.
	 * @param itemName Object the parent item name. This can be either a string or an array.
	 * The latter represents a list of item names.
	 * @return array all child items of the parent
	 */
	public List<Autorizacion> getItemChildren(Object itemName);

	/**
	 * Assigns an authorization item to a user.
	 * @param itemName string the item name
	 * @param userId object the user ID (see {@link IWebUser::getId})
	 * @param bizRule string the business rule to be executed when {@link checkAccess} is called
	 * for this particular authorization item.
	 * @param data object additional data associated with this assignment
	 * @return AutorizacionAsignadot the authorization assignment information.
	 * throws CException if the item does not exist or if the item has already been assigned to the user
	 */
	public AutorizacionAsignado assign(String itemName,Object userId,String bizRule,Object data);
	/**
	 * Revokes an authorization assignment from a user.
	 * @param itemName string the item name
	 * @param userId object the user ID (see {@link IWebUser::getId})
	 * @return boolean whether removal is successful
	 */
	public boolean revoke(String itemName, Object userId);
	/**
	 * Returns a value indicating whether the item has been assigned to the user.
	 * @param itemName string the item name
	 * @param userId Object the user ID (see {@link IWebUser::getId})
	 * @return boolean whether the item has been assigned to the user.
	 */
	public boolean isAssigned(String itemName,Object userId);
	/**
	 * Returns the item assignment information.
	 * @param itemName string the item name
	 * @param userId Object the user ID (see {@link IWebUser::getId})
	 * @return CAuthAssignment the item assignment information. Null is returned if
	 * the item is not assigned to the user.
	 */
	public AutorizacionAsignado getAuthAssignment(String itemName,Object userId);
	/**
	 * Returns the item assignments for the specified user.
	 * @param userId Object the user ID (see {@link IWebUser::getId})
	 * @return array the item assignment information for the user. An empty array will be
	 * returned if there is no item assigned to the user.
	 */
	public List<AutorizacionAsignado> getAuthAssignments(Object userId);
	/**
	 * Saves the changes to an authorization assignment.
	 * @param assignment AutorizacionAsignado the assignment that has been changed.
	 */
	public void saveAuthAssignment(AutorizacionAsignado assignment);

	/**
	 * Removes all authorization data.
	 */
	public void clearAll();
	/**
	 * Removes all authorization assignments.
	 */
	public void clearAuthAssignments();

	/**
	 * Saves authorization data into persistent storage.
	 * If any change is made to the authorization data, please make
	 * sure you call this method to save the changed data into persistent storage.
	 */
	public void save();

	/**
	 * Executes a business rule.
	 * A business rule is a piece of PHP code that will be executed when {@link checkAccess} is called.
	 * @param bizRule string the business rule to be executed.
	 * @param params HaspMap additional parameters to be passed to the business rule when being executed.
	 * @param data Object additional data that is associated with the corresponding authorization item or assignment
	 * @return boolean whether the execution returns a true value.
	 * If the business rule is empty, it will also return true.
	 */
	public boolean executeBizRule(String bizRule,HashMap params,Object data);
}
