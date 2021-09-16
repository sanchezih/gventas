
package autorizacion;

import java.util.List;
import java.util.Objects;

/**
 * CAuthAssignment represents an assignment of a role to a user.
 * It includes additional assignment information such as {@link bizRule} and {@link data}.
 * Do not create a CAuthAssignment instance using the 'new' operator.
 * Instead, call {@link IAuthManager::assign}.
 *
 * @property mixed userId User ID (see {@link IWebUser::getId}).
 * @property string itemName The authorization item name.
 * @property string bizRule The business rule associated with this assignment.
 * @property mixed data Additional data for this assignment.
 * @author Código Lite
 */
public class AutorizacionAsignado {
        private IAuthManager auth;
	private String itemName;
	private Object userId;
	private String bizRule;
	private List<Autorizacion> data;

	/**
	 * Constructor.
	 * @param auth IAuthManager the authorization manager
	 * @param itemName string authorization item name
	 * @param userId mixed user ID (see {@link IWebUser::getId})
	 * @param bizRule stringthe business rule associated with this assignment
	 * @param data mixed additional data for this assignment
	 */
	public AutorizacionAsignado(IAuthManager auth,String itemName,Object userId,String bizRule,List<Autorizacion>data)
	{
		this.auth=auth;
		this.itemName=itemName;
		this.userId=userId;
		this.bizRule=bizRule;
		this.data=data;
	}
        
        public AutorizacionAsignado(IAuthManager auth,String itemName,Object userId,String bizRule)
	{		
                this(auth,itemName,userId,bizRule,null);
	}
        
        public AutorizacionAsignado(IAuthManager auth,String itemName,Object userId)
	{		
                this(auth,itemName,userId,null,null);
	}

	/**
	 * @return object user ID (see {@link IWebUser::getId})
	 */
	public Object getUserId()
	{
		return this.userId;
	}

	/**
	 * @return the string authorization item name
	 */
	public String getItemName()
	{
		return this.itemName;
	}

	/**
	 * @return the string business rule associated with this assignment
	 */
	public String getBizRule()
	{
		return this.bizRule;
	}

	/**
	 * @param value string the business rule associated with this assignment
	 */
	public void setBizRule(String value)
	{
		if(!this.bizRule.equals(value))
		{
			this.bizRule=value;
			this.auth.saveAuthAssignment(this);
		}
	}

	/**
	 * @return additional List<Autorizacion> data for this assignment
	 */
	public List<Autorizacion> getData()
	{
		return this.data;
	}

	/**
	 * @param value List<Autorizacion> additional data for this assignment
	 */
	public void setData(List<Autorizacion> value)
	{
		if(!this.data.equals(value))
		{
			this.data=value;
			this.auth.saveAuthAssignment(this);
		}
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.itemName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AutorizacionAsignado other = (AutorizacionAsignado) obj;
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        return true;
    }
        
        
}
