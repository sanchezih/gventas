package autorizacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Código Lite
 */
public abstract class AutorizacionAdminstrador implements IAuthManager{
    
	/**
	 * @var boolean Enable error reporting for bizRules.
	 * @since 1.1.3
	 */
	public boolean showErrors = false;

	/**
	 * @var array list of role names that are assigned to all users implicitly.
	 * These roles do not need to be explicitly assigned to any user.
	 * When calling {@link checkAccess}, these roles will be checked first.
	 * For performance reason, you should minimize the number of such roles.
	 * A typical usage of such roles is to define an 'authenticated' role and associate
	 * it with a biz rule which checks if the current user is authenticated.
	 * And then declare 'authenticated' in this property so that it can be applied to
	 * every authenticated user.
	 */
	public List defaultRoles= new ArrayList();

	/**
	 * Creates a role.
	 * This is a shortcut method to {@link IAuthManager::createAuthItem}.
	 * @param name string the item name
	 * @param description string the item description.
	 * @param bizRule string the business rule associated with this item
	 * @param data Object additional data to be passed when evaluating the business rule
	 * @return CAuthItem the authorization item
	 */
	public Autorizacion createRole(String name,String description,String bizRule,Object data)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_ROLE,description,bizRule,data);
	}
        public Autorizacion createRole(String name,String description,String bizRule)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_ROLE,description,bizRule,null);
	}
        public Autorizacion createRole(String name,String description)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_ROLE,description,null,null);
	}        
        public Autorizacion createRole(String name)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_ROLE,null,null,null);
	}

	/**
	 * Creates a task.
	 * This is a shortcut method to {@link IAuthManager::createAuthItem}.
	 * @param name string the item name
	 * @param description string the item description.
	 * @param bizRule string the business rule associated with this item
	 * @param data Object additional data to be passed when evaluating the business rule
	 * @return Autorizacion the authorization item
	 */
	public Autorizacion createTask(String name,String description,String bizRule,Object data)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_TASK,description,bizRule,data);
	}
        public Autorizacion createTask(String name,String description,String bizRule)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_TASK,description,bizRule,null);
	}
        public Autorizacion createTask(String name,String description)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_TASK,description,null,null);
	}
        public Autorizacion createTask(String name)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_TASK,null,null,null);
	}
        
        
	/**
	 * Creates an operation.
	 * This is a shortcut method to {@link IAuthManager::createAuthItem}.
	 * @param name string the item name
	 * @param description string the item description.
	 * @param bizRule string the business rule associated with this item
	 * @param data object additional data to be passed when evaluating the business rule
	 * @return Autorizacion the authorization item
	 */
	public Autorizacion createOperation(String name,String description,String bizRule,Object data)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_OPERATION,description,bizRule,data);
	}
        
        public Autorizacion createOperation(String name,String description,String bizRule)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_OPERATION,description,bizRule,null);
	}
        public Autorizacion createOperation(String name,String description)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_OPERATION,description,null,null);
	}
        public Autorizacion createOperation(String name)
	{
		return this.createAuthItem(name,Autorizacion.TYPE_OPERATION,null,null,null);
	}

	/**
	 * Returns roles.
	 * This is a shortcut method to {@link IAuthManager::getAuthItems}.
	 * @param userId Object the user ID. If not null, only the roles directly assigned to the user
	 * will be returned. Otherwise, all roles will be returned.
	 * @return List<Autorizacion> roles (name=>CAuthItem)
	 */
	public List<Autorizacion> getRoles(Object userId)
	{
		return this.getAuthItems(Autorizacion.TYPE_ROLE,userId);
	}
        
        public List<Autorizacion> getRoles()
	{
                return getRoles(null);		
	}

	/**
	 * Returns tasks.
	 * This is a shortcut method to {@link IAuthManager::getAuthItems}.
	 * @param userId mixed the user ID. If not null, only the tasks directly assigned to the user
	 * will be returned. Otherwise, all tasks will be returned.
	 * @return array tasks (name=>CAuthItem)
	 */
	public List<Autorizacion> getTasks(Object userId)
	{
		return this.getAuthItems(Autorizacion.TYPE_TASK,userId);
	}
        
        public List<Autorizacion> getTasks()
	{
		return getTasks(null);
	}

	/**
	 * Returns operations.
	 * This is a shortcut method to {@link IAuthManager::getAuthItems}.
	 * @param mixed $userId the user ID. If not null, only the operations directly assigned to the user
	 * will be returned. Otherwise, all operations will be returned.
	 * @return array operations (name=>CAuthItem)
	 */
	public List<Autorizacion> getOperations(Object userId)
	{
		return this.getAuthItems(Autorizacion.TYPE_OPERATION,userId);
	}
        
        public List<Autorizacion> getOperations()
	{
		return getOperations(null);
	}

	/**
	 * Executes the specified business rule.
	 * @param bizRule string the business rule to be executed.
	 * @param params array $parameters passed to {@link IAuthManager::checkAccess}.
	 * @param data object additional data associated with the authorization item or assignment.
	 * @return boolean whether the business rule returns true.
	 * If the business rule is empty, it will still return true.
	 */
	public boolean executeBizRule(String bizRule,HashMap params,Object data)
	{
            if(bizRule == null)
                return true;
            if(bizRule.isEmpty())
                return true;
           return true;
		//return $bizRule==='' || $bizRule===null || (this.showErrors ? eval($bizRule)!=0 : @eval($bizRule)!=0);
	}

	/**
	 * Checks the item types to make sure a child can be added to a parent.
	 * @param parentType integer parent item type
	 * @param childType integer child item type
	 * throws CException if the item cannot be added as a child due to its incompatible type.
	 */
	protected void checkItemChildType(int parentType,int childType)
	{
		String[] types={"operation","task","role"};
		if(parentType < childType)
                    System.out.println("Error no se puede agregar");
//			throw new CException(Yii::t('yii','Cannot add an item of type "{child}" to an item of type "{parent}".',
//				array('{child}'=>$types[$childType], '{parent}'=>$types[$parentType])));
	}
}
