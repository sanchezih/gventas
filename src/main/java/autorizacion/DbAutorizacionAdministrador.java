package autorizacion;

import core.CriterioSQL;
import managerbd.BaseConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CDbAuthManager represents an authorization manager that stores authorization information in database.
 *
 * The database connection is specified by {@link connectionID}. And the database schema
 * should be as described in "framework/web/auth/*.sql". You may change the names of
 * the three tables used to store the authorization data by setting {@link itemTable},
 * {@link itemChildTable} and {@link assignmentTable}.
 *
 * @property array authItems The authorization items of the specific type.
 * @author Código Lite
 */
public class DbAutorizacionAdministrador extends AutorizacionAdminstrador{
   
    
//        public static final String[] rolesPorDefecto = {"Administrador","Vendedor",
//                                                    "Almacenero","Tecnico"};
    /**
	 * @var string the ID of the {@link CDbConnection} application component. Defaults to 'db'.
	 * The database must have the tables as declared in "framework/web/auth/*.sql".
	 */
	public String connectionID="db";
	/**
	 * @var string the name of the table storing authorization items. Defaults to 'AuthItem'.
	 */
	public String itemTable="gv_authitem";
	/**
	 * @var string the name of the table storing authorization item hierarchy. Defaults to 'AuthItemChild'.
	 */
	public String itemChildTable="gv_authitemchild";
	/**
	 * @var string the name of the table storing authorization item assignments. Defaults to 'AuthAssignment'.
	 */
	public String assignmentTable="gv_asignarauth";
	/**
	 * @var CDbConnection the database connection. By default, this is initialized
	 * automatically as the application component whose ID is indicated as {@link connectionID}.
	 */
	public BaseConexion db;

	private boolean usingSqlite;

	/**
	 * Initializes the application component.
	 * This method overrides the parent implementation by establishing the database connection.
	 */
//	public function init()
//	{
//		parent::init();
//		this._usingSqlite=!strncmp(this.getDbConnection().getDriverName(),'sqlite',6);
//	}
        
        public static HashMap getPermisosPorDefecto()
        {
            HashMap roles = new HashMap();
            ArrayList<String> operacionesVentas = new ArrayList();  
            operacionesVentas.add("registrarVenta");            
            operacionesVentas.add("imprimirVenta");
            operacionesVentas.add("buscarProducto");
            operacionesVentas.add("verStock");
            operacionesVentas.add("registrarCliente");
            operacionesVentas.add("actualizarCliente");
            operacionesVentas.add("eliminarCliente");
            operacionesVentas.add("buscarCliente");
            
            roles.put("vendedor", operacionesVentas);
            
            ArrayList<String> operacionesAlmacenero = new ArrayList();
            operacionesAlmacenero.add("buscarProducto");
            operacionesAlmacenero.add("moverProducto");
            operacionesAlmacenero.add("actualizarStock");
            operacionesAlmacenero.add("registraProducto");
            operacionesAlmacenero.add("actualizarProducto");
            operacionesAlmacenero.add("eliminarProducto");
            operacionesAlmacenero.add("imprimirStock");
            roles.put("almacenero", operacionesAlmacenero);
            
            ArrayList<String> permisosHeredados = new ArrayList();
            permisosHeredados.add("vendedor");//hereda permisoos de vendedor
            permisosHeredados.add("almacenero");//hereda permisoos de almacenero
            permisosHeredados.add("registrarUsuario");
            permisosHeredados.add("eliminarUsuario");
            permisosHeredados.add("actualizarUsuario");
            permisosHeredados.add("buscarUsuario");
            roles.put("administrador", permisosHeredados);
            return roles;
        }

	/**
	 * Performs access check for the specified user.
	 * @param itemName string the name of the operation that need access check
	 * @param userId Object the user ID. This should can be either an integer and a string representing
	 * the unique identifier of a user. See {@link IWebUser::getId}.
	 * @param params Hashmap name-value pairs that would be passed to biz rules associated
	 * with the tasks and roles assigned to the user.
	 * Since version 1.1.11 a param with name 'userId' is added to this array, which holds the value of <code>userId</code>.
	 * @return boolean whether the operations can be performed by the user.
	 */
	public boolean checkAccess(String itemName,Object userId,HashMap params)
	{
		List<AutorizacionAsignado> assignments = this.getAuthAssignments(userId);
		return this.checkAccessRecursive(itemName,userId,params,assignments);
	}

	/**
	 * Performs access check for the specified user.
	 * This method is internally called by {@link checkAccess}.
	 * @param itemName string the name of the operation that need access check
	 * @param userId Objetc the user ID. This should can be either an integer and a string representing
	 * the unique identifier of a user. See {@link IWebUser::getId}.
	 * @param params HashMap name-value pairs that would be passed to biz rules associated
	 * with the tasks and roles assigned to the user.
	 * Since version 1.1.11 a param with name 'userId' is added to this array, which holds the value of <code>userId</code>.
	 * @param  assignments List<AutorizacionAsignado> the assignments to the specified user
	 * @return boolean whether the operations can be performed by the user.
	 * @since 1.1.3
	 */
	protected boolean checkAccessRecursive(String itemName,Object userId,HashMap params,List<AutorizacionAsignado> assignments)
	{            
            Autorizacion item = this.getAuthItem(itemName);
		if(item==null)
			return false;
		//Yii::trace('Checking permission "'.item.getName().'"','system.web.auth.CDbAuthManager');
                if(params == null)
                {
                    params = new HashMap();
                    params.put("userId", userId);
                }else
                {
                    if(!params.containsKey("userId"))
                        params.put("userId", userId);
                }	    
		if(this.executeBizRule(item.getBizRule(),params,item.getData()))
		{                       
			if(this.defaultRoles.contains(itemName))
				return true;
                        AutorizacionAsignado aa = null;
                        for(AutorizacionAsignado tmp:assignments)
                        {
                            if(tmp.getItemName().equals(itemName))
                            {
                                aa = tmp;
                                break;
                            }
                        }
                        
                        if(aa!=null)
                        {
                            if(assignments.contains(aa))
                            {
                                    int index = assignments.indexOf(aa);
                                    AutorizacionAsignado assignment=assignments.get(index);
                                    if(this.executeBizRule(assignment.getBizRule(),params,assignment.getData()))
                                            return true;
                            }
                        }			
                        
                        CriterioSQL criterio = new CriterioSQL(this.itemChildTable);
                        criterio.addCondicion("hijo", "'"+itemName+"'", "=");
                        List<String> parents = this.db.getLista(criterio);
                        for(String parent : parents)
                        {
                            if(this.checkAccessRecursive(parent,userId,params,assignments))
					return true;
                        }
		}
		return false;
	}

	/**
	 * Adds an item as a child of another item.
	 * @param itemName string the parent item name
	 * @param childName string the child item name
	 * @return boolean whether the item is added successfully
	 *  CException if either parent or child doesn't exist or if a loop has been detected.
	 */
	public boolean addItemChild(String itemName,String childName)
	{
		if(itemName.equals(childName))
                {
                    System.out.println("error");
                    return false;
                }
			
                CriterioSQL criterio = new CriterioSQL(this.itemTable);                
                criterio.addCompareTexto("nombre", itemName,null,"=");
                criterio.addCompareTexto("nombre", childName, "or","=");
            HashMap rows = BaseConexion.getItems(criterio);
		if(rows.size()==2)
		{
                        ArrayList reg = (ArrayList)rows.get(0);
                        ArrayList reg1 = (ArrayList)rows.get(1);
                        int parentType;
                        int childType;
			if(reg.get(0).equals(itemName))
			{
                                //obtener el valor del campo tipo
				parentType=Integer.parseInt(reg.get(1).toString());
				childType=Integer.parseInt(reg1.get(1).toString());
			}
			else
			{
				parentType=Integer.parseInt(reg.get(1).toString());
				childType=Integer.parseInt(reg1.get(1).toString());
			}
			this.checkItemChildType(parentType,childType);
			if(this.detectLoop(itemName,childName))
                        {
                            System.out.println("No se se puede agregar como hijo");
                            return false;
                        }
//				throw new CException(Yii::t('yii','Cannot add "{child}" as a child of "{name}". A loop has been detected.',
//					array('{child}'=>childName,'{name}'=>itemName)));
                    Statement st = BaseConexion.getStatement();
                    try {
                        System.out.println();
                        st.execute("insert into "+this.itemChildTable+"(padre, hijo) values('"+itemName+"','"+childName+"')");
//			this.db.createCommand()
//				.insert(this.itemChildTable, array(
//					'parent'=>itemName,
//					'child'=>childName,
//				));
                    } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }

			return true;
		}
		else
                {
                    System.out.println("No existe padre ni hijo");
                    return false;
                }
                    
			//throw new CException(Yii::t('yii','Either "{parent}" or "{child}" does not exist.',array('{child}'=>childName,'{parent}'=>itemName)));
	}

	/**
	 * Removes a child from its parent.
	 * Note, the child item is not deleted. Only the parent-child relationship is removed.
	 * @param itemName string the parent item name
	 * @param childName string the child item name
	 * @return boolean whether the removal is successful
	 */
	public boolean removeItemChild(String itemName,String childName)
	{
            boolean delete = false;
                Statement st = BaseConexion.getStatement();
                try {
                    delete = st.executeUpdate("delete from "+this.itemChildTable+" where padre ='"+itemName+"' and hijo='"+childName+"'")>0;
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                return delete;
//		return this.db.createCommand()
//			.delete(this.itemChildTable, 'parent=:parent AND child=:child', array(
//				':parent'=>itemName,
//				':child'=>childName
//			)) > 0;
	}

	/**
	 * Returns a value indicating whether a child exists within a parent.
	 * @param itemName string the parent item name
	 * @param childName string the child item name
	 * @return boolean whether the child exists
	 */
	public boolean hasItemChild(String itemName,String childName)
	{
                boolean encontrado = false;
                Statement st = BaseConexion.getStatement();
                try {
                    ResultSet rs = st.executeQuery("select padre from "+this.itemChildTable+" where padre ='"+itemName+"' and hijo='"+childName+"'");
                    while(rs.next())
                    {
                        encontrado = true;
                    }
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                return encontrado ;
//		return this.db.createCommand()
//			.select('parent')
//			.from(this.itemChildTable)
//			.where('parent=:parent AND child=:child', array(
//				':parent'=>itemName,
//				':child'=>childName))
//			.queryScalar() !== false;
	}

	/**
	 * Returns the children of the specified item.
	 * @param names Objetc the parent item name. This can be either a string or an array.
	 * The latter represents a list of item names.
	 * @return array all child items of the parent
	 */
	public List<Autorizacion> getItemChildren(Object names)
	{
                String condition="";
		if(names instanceof String)
			condition="padre='"+names+"'";
                else if(names instanceof List)
		{
                        List ns = (List)names;
                        String unirNombres="";
                        for(Object name:ns)
                        {
                            if(ns.indexOf(name)==0)
                            {
                                unirNombres+="'"+name+"'";
                            }else{
                                unirNombres+=",'"+name+"'";
                            }
                        }
                        condition="padre IN ("+unirNombres+")";
//			foreach(names as &name)
//				name=this.db.quoteValue(name);
//			condition='parent IN ('.implode(', ',names).')';
		}    
                
                String sql = "select nombre, tipo, descripcion, bizrule, dato from "; 
                sql += this.itemTable+", "+this.itemChildTable+" where "+condition;
                sql += " AND nombre=hijo";
                System.out.println(sql);
            ArrayList<HashMap> rows = BaseConexion.getItemChildrens(sql);
                
//		rows=this.db.createCommand()
//			.select('name, type, description, bizrule, data')
//			.from(array(
//				this.itemTable,
//				this.itemChildTable
//			))
//			.where(condition.' AND name=child')
//			.queryAll();

		List<Autorizacion> children=new ArrayList();
                Autorizacion auth;
                for(HashMap row:rows)
                {
                    auth = new Autorizacion(this,row.get("nombre").toString(),(Integer)row.get("tipo"),String.valueOf(row.get("descripcion")),String.valueOf(row.get("bizrule")),null);
                    children.add(auth);
                }
//		foreach(rows as row)
//		{
//			if((data=@unserialize(row['data']))===false)
//				data=null;
//			children[row['name']]=new CAuthItem(this,row['name'],row['type'],row['description'],row['bizrule'],data);
//		}
		return children;
	}

	/**
	 * Assigns an authorization item to a user.
	 * @param itemName string the item name
	 * @param userId Object the user ID (see {@link IWebUser::getId})
	 * @param bizRule string the business rule to be executed when {@link checkAccess} is called
	 * for this particular authorization item.
	 * @param data Objetc additional data associated with this assignment
	 * @return CAuthAssignment the authorization assignment information.
	 *  CException if the item does not exist or if the item has already been assigned to the user
	 */
	public AutorizacionAsignado assign(String itemName, Object userId,String bizRule,Object data)
	{
		if(this.usingSqlite() && this.getAuthItem(itemName)==null)
                {
                    System.out.println("Error......");
                    return null;
                    //throw new CException(Yii::t('yii','The item "{name}" does not exist.',array('{name}'=>itemName)));
                }
			
               Statement st = BaseConexion.getStatement();
                    try {
                        st.execute("insert into "+this.assignmentTable+"(nombreitem,iduser,bizrule,dato) values('"+itemName+"','"+userId+"','"+bizRule+"','"+data+"')");
//			
                    } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }                
//                
//                
//		this.db.createCommand()
//			.insert(this.assignmentTable, array(
//				'itemname'=>itemName,
//				'userid'=>userId,
//				'bizrule'=>bizRule,
//				'data'=>serialize(data)
//			));
		//return new AutorizacionAsignado(this,itemName,userId,bizRule,data);
                 return new AutorizacionAsignado(this,itemName,userId,bizRule,null);   
	}
        
        public AutorizacionAsignado assign(String itemName, Object userId,String bizRule)
        {
            return assign(itemName,userId,bizRule,"");
        }
        
        public AutorizacionAsignado assign(String itemName, Object userId)
        {
            return assign(itemName,userId,"","");
        }

	/**
	 * Revokes an authorization assignment from a user.
	 * @param itemName string the item name
	 * @param userId Objetc the user ID (see {@link IWebUser::getId})
	 * @return boolean whether removal is successful
	 */
	public boolean revoke(String itemName,Object userId)
	{
                boolean revocado = false;
            
                Statement st = BaseConexion.getStatement();
                    try {
                        revocado = st.executeUpdate("delete from "+this.assignmentTable+" where nombreitem='"+itemName+"' and iduser='"+userId+"'")>0;
//			
                    } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    } 
//		return this.db.createCommand()
//			.delete(this.assignmentTable, 'itemname=:itemname AND userid=:userid', array(
//				':itemname'=>itemName,
//				':userid'=>userId
//			)) > 0;
                    return revocado;
                    
	}

	/**
	 * Returns a value indicating whether the item has been assigned to the user.
	 * @param string itemName the item name
	 * @param mixed userId the user ID (see {@link IWebUser::getId})
	 * @return boolean whether the item has been assigned to the user.
	 */
	public boolean isAssigned(String itemName,Object userId)
	{
            boolean encontrado = false;
                 Statement st = BaseConexion.getStatement();
                try {
                    ResultSet rs = st.executeQuery("select nombreitem from "+this.assignmentTable+" where nombreitem ='"+itemName+"' and iduser='"+userId+"'");
                    while(rs.next())
                    {
                        encontrado = true;
                    }
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
//		return this.db.createCommand()
//			.select('itemname')
//			.from(this.assignmentTable)
//			.where('itemname=:itemname AND userid=:userid', array(
//				':itemname'=>itemName,
//				':userid'=>userId))
//			.queryScalar() !== false;
             return encontrado;           
        }

	/**
	 * Returns the item assignment information.
	 * @param string itemName the item name
	 * @param mixed userId the user ID (see {@link IWebUser::getId})
	 * @return CAuthAssignment the item assignment information. Null is returned if
	 * the item is not assigned to the user.
	 */
	public AutorizacionAsignado getAuthAssignment(String itemName,Object userId)
	{
            
                 Statement st = BaseConexion.getStatement();
                 HashMap row = new HashMap();
                try {
                    ResultSet rs = st.executeQuery("select * from "+this.assignmentTable+" where nombreitem ='"+itemName+"' and iduser='"+userId+"'");
                    while(rs.next())
                    {
                        row.put("nombreitem", rs.getString(1));
                        row.put("iduser", rs.getInt(2));
                        row.put("bizrule", rs.getString(3));
                        row.put("dato", rs.getString(4));                        
                    }
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
//		row=this.db.createCommand()
//			.select()
//			.from(this.assignmentTable)
//			.where('itemname=:itemname AND userid=:userid', array(
//				':itemname'=>itemName,
//				':userid'=>userId))
//			.queryRow();
                if(row.size()>0)
                {
                    return new AutorizacionAsignado(this,row.get("nombreitem").toString(),row.get("iduser"),String.valueOf(row.get("bizrule")),null);
                }else
                    return null;
//		if(row!==false)
//		{
//			if((data=@unserialize(row['data']))===false)
//				data=null;
//			return new CAuthAssignment(this,row['itemname'],row['userid'],row['bizrule'],data);
//		}
//		else
//			return null;
	}

	/**
	 * Returns the item assignments for the specified user.
	 * @param userId Object the user ID (see {@link IWebUser::getId})
	 * @return array the item assignment information for the user. An empty array will be
	 * returned if there is no item assigned to the user.
	 */
	public ArrayList<AutorizacionAsignado> getAuthAssignments(Object userId)
	{
                Statement st = BaseConexion.getStatement();
                 HashMap row;
                 ArrayList<HashMap>rows = new ArrayList();
                try {
                    ResultSet rs = st.executeQuery("select * from "+this.assignmentTable+" where iduser='"+userId+"'");
                    while(rs.next())
                    {
                        row = new HashMap();
                        row.put("nombreitem", rs.getString(1));
                        row.put("iduser", rs.getString(2));
                        row.put("bizrule", rs.getString(3));
                        row.put("dato", rs.getString(4));    
                        rows.add(row);
                    }
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
//		rows=this.db.createCommand()
//			.select()
//			.from(this.assignmentTable)
//			.where('userid=:userid', array(':userid'=>userId))
//			.queryAll();
                
                ArrayList<AutorizacionAsignado> assignments=new ArrayList();
//		foreach(rows as row)
//		{
//			if((data=@unserialize(row['data']))===false)
//				data=null;
//			assignments[row['itemname']]=new CAuthAssignment(this,row['itemname'],row['userid'],row['bizrule'],data);
//		}
                for(HashMap row1:rows)
                {
                    AutorizacionAsignado aa = new AutorizacionAsignado(this,row1.get("nombreitem").toString(),row1.get("iduser"),String.valueOf(row1.get("bizrule")),null);
                    assignments.add(aa);
                }
		
		return assignments;
	}

	/**
	 * Saves the changes to an authorization assignment.
	 * @param assignment AutorizacionAsignado the assignment that has been changed.
	 */
	public void saveAuthAssignment(AutorizacionAsignado assignment)
	{
            
                try {
                    Statement st = BaseConexion.getStatement();
                    st.executeUpdate("update "+this.assignmentTable+" set bizrule='"+assignment.getBizRule()+"', dato = '"+assignment.getData()+"' where nombreitem='"+assignment.getItemName()+"' and iduser='"+assignment.getUserId()+"'");
                   
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
//            
//		this.db.createCommand()
//			.update(this.assignmentTable, array(
//				'bizrule'=>assignment.getBizRule(),
//				'data'=>serialize(assignment.getData()),
//			), 'itemname=:itemname AND userid=:userid', array(
//				'itemname'=>assignment.getItemName(),
//				'userid'=>assignment.getUserId()
//			));
	}

	/**
	 * Returns the authorization items of the specific type and user.
	 * @param type integer the item type (0: operation, 1: task, 2: role). Defaults to null,
	 * meaning returning all items regardless of their type.
	 * @param userId Objetc the user ID. Defaults to null, meaning returning all items even if
	 * they are not assigned to a user.
	 * @return array the authorization items of the specific type.
	 */
	public ArrayList<Autorizacion> getAuthItems(Integer type,Object userId)
	{
                String command = "";
		if(type==null && userId==null)
		{
                        command = "select * from "+this.itemTable;
//			command=this.db.createCommand()
//				.select()
//				.from(this.itemTable);
		}
                else if(userId==null)
		{
                        command = "select * from "+this.itemTable+" where tipo="+type;
//			command=this.db.createCommand()
//				.select()
//				.from(this.itemTable)
//				.where('type=:type', array(':type'=>type));
		}
                else if(type==null)
		{
                        command = "select nombre,tipo,descripcion,t1.bizrule,t1.dato from "+this.itemTable+" as t1,"+this.assignmentTable+" as t2  where nombre=nombreitem and iduser='"+userId+"'";
//			command=this.db.createCommand()
//				.select('name,type,description,t1.bizrule,t1.data')
//				.from(array(
//					this.itemTable.' t1',
//					this.assignmentTable.' t2'
//				))
//				.where('name=itemname AND userid=:userid', array(':userid'=>userId));
		}
		else
		{
                    
                    command = "select nombre,tipo,descripcion,t1.bizrule,t1.dato from "+this.itemTable+" as t1,"+this.assignmentTable+" as t2  where nombre=nombreitem and tipo="+type+" and iduser='"+userId+"'";
//			command=this.db.createCommand()
//				.select('name,type,description,t1.bizrule,t1.data')
//				.from(array(
//					this.itemTable.' t1',
//					this.assignmentTable.' t2'
//				))
//				.where('name=itemname AND type=:type AND userid=:userid', array(
//					':type'=>type,
//					':userid'=>userId
//				));
		}
                
                ArrayList<Autorizacion> items=new ArrayList();
		//items=array();
                
                
                Statement st = BaseConexion.getStatement();
                 HashMap row;
                 ArrayList<HashMap>rows = new ArrayList();
                try {
                    ResultSet rs = st.executeQuery(command);
                    while(rs.next())
                    {
                        row = new HashMap();
                        row.put("nombre", rs.getString(1));
                        row.put("tipo", rs.getInt(2));
                        row.put("descripcion", rs.getString(3));
                        row.put("bizrule", rs.getString(4)); 
                        row.put("dato", rs.getString(5)); 
                        rows.add(row);
                    }
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                
                
//		foreach(command.queryAll() as row)
//		{
//			if((data=@unserialize(row['data']))===false)
//				data=null;
//			items[row['name']]=new CAuthItem(this,row['name'],row['type'],row['description'],row['bizrule'],data);
//		}
                
                Autorizacion nauth;
                for(HashMap row1:rows)
		{
			nauth = new Autorizacion(this,row1.get("nombre").toString(),(Integer)row1.get("tipo"),String.valueOf(row1.get("descripcion")),String.valueOf(row1.get("bizrule")),null);
			items.add(nauth);
		}
		return items;
	}
        
        public ArrayList<Autorizacion> getAuthItems(int type)
        {
            return getAuthItems(type,null);
        }
        
        public ArrayList<Autorizacion> getAuthItems()
        {
            return getAuthItems(null,null);
        }

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
	 * @param data Objetc additional data associated with the item.
	 * @return CAuthItem the authorization item
	 * CException if an item with the same name already exists
	 */
	public Autorizacion createAuthItem(String name,int type, String description,String bizRule,Object data)
	{
                
                   System.out.println("insert into "+this.itemTable+"(nombre,tipo,descripcion,bizrule,dato) values('"+name+"',"+type+",'"+description+"','"+bizRule+"','"+data+"')");
                    Statement st = BaseConexion.getStatement();
                    try {
                        boolean execute = st.execute("insert into "+this.itemTable+"(nombre,tipo,descripcion,bizrule,dato) values('"+name+"',"+type+",'"+description+"','"+bizRule+"','"+data+"')");
//			
                    } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    } 
//		this.db.createCommand()
//			.insert(this.itemTable, array(
//				'name'=>name,
//				'type'=>type,
//				'description'=>description,
//				'bizrule'=>bizRule,
//				'data'=>serialize(data)
//			));
		return new Autorizacion(this,name,type,description,bizRule,null);
	}
        
        public Autorizacion createAuthItem(String name,int type, String description,String bizRule)
        {
            return createAuthItem(name,type, description,bizRule,null);
        }
        
        public Autorizacion createAuthItem(String name,int type, String description)
        {
            return createAuthItem(name,type, description,null,null);
        }
        
        public Autorizacion createAuthItem(String name,int type)
        {
            return createAuthItem(name,type,"",null,null);
        }
        
        
        

	/**
	 * Removes the specified authorization item.
	 * @param name string the name of the item to be removed
	 * @return boolean whether the item exists in the storage and has been removed
	 */
	public boolean removeAuthItem(String name)
	{
//		if(this.usingSqlite())
//		{
//			this.db.createCommand()
//				.delete(this.itemChildTable, 'parent=:name1 OR child=:name2', array(
//					':name1'=>name,
//					':name2'=>name
//			));
//			this.db.createCommand()
//				.delete(this.assignmentTable, 'itemname=:name', array(
//					':name'=>name,
//			));
//		}

            boolean revocado = false;
            
                Statement st = BaseConexion.getStatement();
                    try {
                        revocado = st.executeUpdate("delete from "+this.itemTable+" where nombre='"+name+"'")>0;
//			
                    } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
//		return this.db.createCommand()
//			.delete(this.itemTable, 'name=:name', array(
//				':name'=>name
//			)) > 0;
                    return revocado;
	}

	/**
	 * Returns the authorization item with the specified name.
	 * @param name string the name of the item
	 * @return CAuthItem the authorization item. Null if the item cannot be found.
	 */
	public Autorizacion getAuthItem(String name)
	{
            
//		row=this.db.createCommand()
//			.select()
//			.from(this.itemTable)
//			.where('name=:name', array(':name'=>name))
//			.queryRow();
//                        
                        
                 Statement st = BaseConexion.getStatement();
                 HashMap row = new HashMap();
                try {
                    ResultSet rs = st.executeQuery("select * from "+this.itemTable+" where nombre ='"+name+"'");
                    while(rs.next())
                    {
                        row.put("nombre", rs.getString(1));
                        row.put("tipo", rs.getInt(2));
                        row.put("descripcion", rs.getString(3));
                        row.put("bizrule", rs.getString(4));
                        row.put("dato", rs.getObject(5));                        
                    }
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }        
                        

//		if(row!==false)
//		{
//			if((data=@unserialize(row['data']))===false)
//				data=null;
//			return new CAuthItem(this,row['name'],row['type'],row['description'],row['bizrule'],data);
//		}
//		else
//                    return null;
                if(row.size()>0)
                {
                    return new Autorizacion(this,row.get("nombre").toString(),(Integer)row.get("tipo"),String.valueOf(row.get("descripcion")),String.valueOf(row.get("bizrule")),null);
                }
                else
                    return null;
                
	}

	/**
	 * Saves an authorization item to persistent storage.
	 * @param item Autorizacion the item to be saved.
	 * @param oldName string  the old item name. If null, it means the item name is not changed.
	 */
	public void saveAuthItem(Autorizacion item,String oldName)
	{
//		if(this.usingSqlite() && oldName!==null && item.getName()!==oldName)
//		{
//			this.db.createCommand()
//				.update(this.itemChildTable, array(
//					'parent'=>item.getName(),
//				), 'parent=:whereName', array(
//					':whereName'=>oldName,
//				));
//			this.db.createCommand()
//				.update(this.itemChildTable, array(
//					'child'=>item.getName(),
//				), 'child=:whereName', array(
//					':whereName'=>oldName,
//				));
//			this.db.createCommand()
//				.update(this.assignmentTable, array(
//					'itemname'=>item.getName(),
//				), 'itemname=:whereName', array(
//					':whereName'=>oldName,
//				));
//		}

//		this.db.createCommand()
//			.update(this.itemTable, array(
//				'name'=>item.getName(),
//				'type'=>item.getType(),
//				'description'=>item.getDescription(),
//				'bizrule'=>item.getBizRule(),
//				'data'=>serialize(item.getData()),
//			), 'name=:whereName', array(
//				':whereName'=>oldName===null?item.getName():oldName,
//			));
                 try {
                    Statement st = BaseConexion.getStatement();
                    st.executeUpdate("update "+this.itemTable+" set nombre='"+item.getName()+"', tipo = "+item.getType()+", descripcion = '"+
                            item.getDescription()+"', bizrule='"+item.getBizRule()+"', dato = '"+item.getData()+"'where nombre='"+(oldName==null?item.getName():oldName)+"'");
                   
                } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }               
                                
	}
        
        public void saveAuthItem(Autorizacion item)
        {
            saveAuthItem(item,null);
        }
        
	/**
	 * Saves the authorization data to persistent storage.
	 */
	public void save()
	{
	}

	/**
	 * Removes all authorization data.
	 */
	public void clearAll()
	{
		this.clearAuthAssignments();
//		this.db.createCommand().delete(this.itemChildTable);
//		this.db.createCommand().delete(this.itemTable);
                
                //boolean revocado = false;            
                Statement st = BaseConexion.getStatement();
                    try {
                        st.executeUpdate("delete from "+this.itemChildTable);
                        st.executeUpdate("delete from "+this.itemTable);
//			
                    } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
	}

	/**
	 * Removes all authorization assignments.
	 */
	public void clearAuthAssignments()
	{
//		this.db.createCommand().delete(this.assignmentTable);
                Statement st = BaseConexion.getStatement();
                    try {
                        st.executeUpdate("delete from "+this.assignmentTable);
//			
                    } catch (SQLException ex) {
                        Logger.getLogger(DbAutorizacionAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                    }
	}

	/**
	 * Checks whether there is a loop in the authorization item hierarchy.
	 * @param itemName string parent item name
	 * @param childName string the name of the child item that is to be added to the hierarchy
	 * @return boolean whether a loop exists
	 */
	protected boolean detectLoop(String itemName,String childName)
	{
		if(childName.equals(itemName))
			return true;
            List<Autorizacion> itemChildren = this.getItemChildren(childName);
		for(Autorizacion child : itemChildren)
		{
			if(this.detectLoop(itemName,child.getName()))
				return true;
		}
		return false;
	}

	/**
	 * @return BaseConexion the DB connection instance
	 * CException if {@link connectionID} does not point to a valid application component.
	 */
	protected BaseConexion getDbConnection()
	{
		if(this.db!=null)
			return this.db; 
                return null;
	}

	/**
	 * @return boolean whether the database is a SQLite database
	 */
	protected boolean usingSqlite()
	{
		return this.usingSqlite;
	}

}
