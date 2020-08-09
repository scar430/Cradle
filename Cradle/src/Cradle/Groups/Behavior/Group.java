package Cradle.Groups.Behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.permissions.PermissionAttachment;

public class Group {
	
	public Group parent = null;
	public String prefix = "";
	public String suffix = "";
	
	// All users in group
	public HashMap<UUID, PermissionAttachment> Users = new HashMap<UUID, PermissionAttachment>();
	
	// All permissions allotted to this group.
	private ArrayList<String> permissions = new ArrayList<String>();
	
	public void loadPermissions(ArrayList<String> perms) {
		permissions = perms;
	}
	
	public void addPermission(String permToAdd) {
		permissions.add(permToAdd);
		updatePermissions();
	}
	
	public void removePermission(String permToRemove) {
		permissions.remove(permToRemove);
	}
	
	private void updatePermissions() {
		// TO DO : This needs to account for parenting.
		for (PermissionAttachment attachment : Users.values()) {
			for(String perm : permissions) {
				attachment.setPermission(perm, true);
			}
		}
		
	}
}
