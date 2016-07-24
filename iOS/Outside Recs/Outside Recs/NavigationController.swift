//
//  NavigationController.swift
//  Outside Recs
//
//  Created by Alex Kazorian on 7/23/16.
//  Copyright © 2016 AlexK. All rights reserved.
//

import UIKit
import Material
import SnapKit

class NavigationController: UINavigationController {
    
    var auth = SPTAuth.defaultInstance()

    override func viewDidLoad() {
        super.viewDidLoad()
        //let shadowBar : UIView = UIView(frame: CGRect(x: 0, y: 0, width: self.view.bounds.width, height: self.navigationBar.bounds.height + 10))
        //shadowBar.backgroundColor = MaterialColor.grey.lighten1
        self.navigationBar.barTintColor = MaterialColor.orange.darken1
        self.navigationBar.titleTextAttributes = [UIFontDescriptorTextStyleAttribute: MaterialFont.boldSystemFontWithSize(20), NSForegroundColorAttributeName: MaterialColor.white]
        
        prepareStatusBarTint()
        prepareLogoutButton()
        //self.navigationBar.titleTextAttributes = [UIFontDescriptorTextStyleAttribute: MaterialFont.boldSystemFontWithSize(20)]
        // Do any additional setup after loading the view.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func prepareLogoutButton() {
        let logoutButtonImage = MaterialIcon.cm.close
        let logoutButton: FabButton = FabButton(frame: CGRect(x: (self.view.bounds.width - 28) / 2, y: 200, width: 56, height: 56))
        logoutButton.backgroundColor = MaterialColor.orange.darken1
        logoutButton.setImage(logoutButtonImage, forState: .Normal)
        logoutButton.setImage(logoutButtonImage, forState: .Highlighted)
        logoutButton.addTarget(self, action: #selector(NavigationController.didPressLogoutButton(_:)), forControlEvents: .TouchUpInside)
        self.view.addSubview(logoutButton)
        
        logoutButton.snp_makeConstraints { (make) -> Void in
            make.width.equalTo(56)
            make.height.equalTo(56)
            make.rightMargin.equalTo(self.view.snp_rightMargin).offset(-10)
            make.bottomMargin.equalTo(self.view.snp_bottomMargin).offset(-20)
        }
    }
    
    @objc private func didPressLogoutButton(sender: FabButton!) {
        self.performSegueWithIdentifier("backToLogin", sender: self)
    }
    
    private func prepareStatusBarTint() {
        let statusBarTint : UIView = UIView(frame: CGRect(x: 0, y: 0, width: self.view.bounds.width, height: 28))
        statusBarTint.backgroundColor = MaterialColor.orange.darken2
        self.view.addSubview(statusBarTint)
        
        statusBarTint.snp_makeConstraints{ (make) -> Void in
            make.width.equalTo(self.view.bounds.width)
            make.height.equalTo(28)
            make.topMargin.equalTo(self.view.snp_topMargin)
        }
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
